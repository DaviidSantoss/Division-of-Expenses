package Santos.David.Service;

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


    public CostService(CostRepository costRepository,
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
    public Cost createCost(CostDTO dto) {

        // TODO:  CRIAR EXCEÇÃO CORRETA
        var group = groupRepository.findById(dto.getGroupId()).orElseThrow(() -> new RuntimeException("Group not found"));

        // TODO:  CRIAR EXCEÇÃO CORRETA
        var paidBy = userRepository.findById(dto.getPaidFor()).orElseThrow(() -> new RuntimeException("User not found"));

        var cost = new Cost();
        cost.setGroups(group);
        cost.setPaidFor(paidBy);
        cost.setDescription(dto.getDescription());
        cost.setTotalValue(dto.getTotalValue());
        cost.setType(dto.getType());
        cost.setCreatedIn(LocalDateTime.now());
        var savedCost = costRepository.save(cost);

        if(cost.getType() == Cost.TypeOfDivision.EQUALITY){
            generateEqualDivisions(savedCost, group);
        }else{
            generateByValueDivisions(savedCost, dto.getDivisions());
        }

        return savedCost;

    }

    private void generateEqualDivisions(Cost cost, Groups group) {

        var members = groupOfMembersRepository.findByGroup(group);

        // TODO: declarar exceção correta
        if (members.isEmpty()) {
            throw new RuntimeException("Group has no members");
        }

        BigDecimal share = cost.getTotalValue().divide(BigDecimal.valueOf(members.size()),2, RoundingMode.HALF_UP);

        for (var member : members) {
            var division = new ExpenseDivision();
            division.setCost(cost);
            division.setUser(member.getUser());
            division.setValueExpense(share);
            expenseDivisionRepository.save(division);
        }
    }


    private void generateByValueDivisions(Cost cost, List<DivisionItemDTO> divisions) {

        BigDecimal sum =  divisions.stream().map(DivisionItemDTO::getValue).reduce(BigDecimal.ZERO,BigDecimal::add);

        if(sum.compareTo(cost.getTotalValue()) != 0){
            throw new RuntimeException("Sum of divisions does not match total value");
        }

        for(var item : divisions){
            var user = userRepository.findById(item.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            var division = new ExpenseDivision();
            division.setCost(cost);
            division.setUser(user);
            division.setValueExpense(item.getValue());
            expenseDivisionRepository.save(division);
        }


    }

}
