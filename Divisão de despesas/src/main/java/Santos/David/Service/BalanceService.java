package Santos.David.Service;

import Santos.David.Exception.GroupNotFoundException;
import Santos.David.Model.Groups;
import Santos.David.Model.User;
import Santos.David.Service.Repository.ExpenseDivisionRepository;
import Santos.David.Service.Repository.GroupOfMembersRepository;
import Santos.David.Service.Repository.GroupRepository;
import Santos.David.Service.Repository.PaymentRepository;
import Santos.David.dto.MemberBalanceDTO;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceService {


    private final  GroupRepository groupRepository;
    private final GroupOfMembersRepository groupOfMembersRepository;
    private final ExpenseDivisionRepository expenseDivisionRepository;
    private final PaymentRepository paymentRepository;

    public BalanceService(GroupRepository groupRepository, GroupOfMembersRepository groupOfMembersRepository, ExpenseDivisionRepository expenseDivisionRepository, PaymentRepository paymentRepository) {
        this.groupRepository = groupRepository;
        this.groupOfMembersRepository = groupOfMembersRepository;
        this.expenseDivisionRepository = expenseDivisionRepository;
        this.paymentRepository = paymentRepository;
    }


    public List<MemberBalanceDTO> getBalance(Long groupId){

        var findGroup = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException());
        var findAllMermbersGroup = groupOfMembersRepository.findByGroups(findGroup);

        List<MemberBalanceDTO> list = new ArrayList<>();

        for(var member : findAllMermbersGroup){

            var user = member.getUser();
            list.add(calculateMemberBalance(user,findGroup));
        }

        return list;
    }


    private MemberBalanceDTO calculateMemberBalance(User user, Groups group){

        var totalDue = expenseDivisionRepository.sumValueExpenseByUserAndGroup(user, group);
        var totalPaid = paymentRepository.sumValueByUserAndGroup(user, group);
        if(totalPaid == null) totalDue = BigDecimal.ZERO;
        if(totalPaid == null) totalPaid = BigDecimal.ZERO;

        BigDecimal totalBalance = totalDue.subtract(totalPaid);

        MemberBalanceDTO memberBalanceDTO = new MemberBalanceDTO();
        memberBalanceDTO.setUserName(user.getName());
        memberBalanceDTO.setBalance(totalBalance);

        return memberBalanceDTO;
    }



}
