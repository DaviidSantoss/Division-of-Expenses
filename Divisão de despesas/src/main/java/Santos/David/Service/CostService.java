package Santos.David.Service;

import Santos.David.Exception.BillSplitAmountMismatchException;
import Santos.David.Exception.EmptyGroupException;
import Santos.David.Exception.GroupNotFoundException;
import Santos.David.Exception.UserNotFoundException;
import Santos.David.Model.Cost;
import Santos.David.Model.ExpenseDivision;
import Santos.David.Model.Groups;
import Santos.David.Service.Repository.*;
import Santos.David.dto.CostDTO;
import Santos.David.dto.DivisionItemDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CostService {


    private final CostRepository costRepository;
    private final ExpenseDivisionRepository expenseDivisionRepository;
    private final GroupRepository groupRepository;
    private final GroupOfMembersRepository groupOfMembersRepository;
    private final UserRepository userRepository;

    public CostService(
            CostRepository costRepository,
            ExpenseDivisionRepository expenseDivisionRepository,
            GroupRepository groupRepository,
            GroupOfMembersRepository groupOfMembersRepository,
            UserRepository userRepository) {

        this.costRepository = costRepository;
        this.expenseDivisionRepository = expenseDivisionRepository;
        this.groupRepository = groupRepository;
        this.groupOfMembersRepository = groupOfMembersRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public CostDTO createCost(CostDTO dto) {

        var group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException());

        var paidBy = userRepository.findById(dto.getPaidFor())
                .orElseThrow(() -> new UserNotFoundException());

        var cost = new Cost();
        cost.setGroups(group);
        cost.setPaidFor(paidBy);
        cost.setDescription(dto.getDescription());
        cost.setTotalValue(dto.getTotalValue());
        cost.setType(dto.getType());
        cost.setCreatedIn(LocalDateTime.now());

        var savedCost = costRepository.save(cost);

        if (savedCost.getType() == Cost.TypeOfDivision.EQUALITY) {
            generateEqualDivisions(savedCost, group);
        } else {
            generateByValueDivisions(savedCost, dto.getDivisions());
        }

        return convertToDTO(savedCost);
    }


    private CostDTO convertToDTO(Cost cost) {

        CostDTO dto = new CostDTO();

        dto.setId(cost.getId());
        dto.setGroupId(cost.getGroups().getId());
        dto.setPaidFor(cost.getPaidFor().getId());
        dto.setDescription(cost.getDescription());
        dto.setTotalValue(cost.getTotalValue());
        dto.setType(cost.getType());
        dto.setCreatedIn(cost.getCreatedIn());

        return dto;
    }


    private void generateEqualDivisions(Cost cost, Groups group) {

        var members = groupOfMembersRepository.findByGroups(group);

        if (members.isEmpty()) {
            throw new EmptyGroupException();
        }

        BigDecimal share = cost.getTotalValue().divide(
                BigDecimal.valueOf(members.size()),
                2,
                RoundingMode.HALF_UP
        );

        for (var member : members) {

            var division = new ExpenseDivision();
            division.setCost(cost);
            division.setUser(member.getUser());
            division.setValueExpense(share);

            expenseDivisionRepository.save(division);
        }
    }


    private void generateByValueDivisions(
            Cost cost,
            List<DivisionItemDTO> divisions) {

        BigDecimal sum = divisions.stream()
                .map(DivisionItemDTO::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sum.compareTo(cost.getTotalValue()) != 0) {
            throw new BillSplitAmountMismatchException();
        }

        for (var item : divisions) {
            var user = userRepository.findById(item.getUserId())
                    .orElseThrow(() -> new UserNotFoundException());

            var division = new ExpenseDivision();
            division.setCost(cost);
            division.setUser(user);
            division.setValueExpense(item.getValue());

            expenseDivisionRepository.save(division);
        }
    }


    public List<CostDTO> findCostsByGroup(Long groupId) {

        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException());

        var costs = costRepository.findByGroups(group);

        return costs.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
