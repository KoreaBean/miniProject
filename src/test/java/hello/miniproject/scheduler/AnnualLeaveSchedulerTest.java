package hello.miniproject.scheduler;


import hello.miniproject.entity.EmployeeEntity;
import hello.miniproject.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AnnualLeaveSchedulerTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private AnnualLeaveScheduler annualLeaveScheduler;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 유저연차테스트(){
        //given
        EmployeeEntity employee = new EmployeeEntity();
        employee.setEmployeeId(1L);
        employee.setName("김상균");
        employee.setWorkStartDate("2024-09-08 13:13:13");
        //when

        //then

    }

}