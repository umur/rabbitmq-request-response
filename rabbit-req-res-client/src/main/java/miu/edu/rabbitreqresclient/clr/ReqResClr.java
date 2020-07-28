package miu.edu.rabbitreqresclient.clr;

import miu.edu.rabbitreqresclient.service.SimpleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReqResClr implements CommandLineRunner {

    private final SimpleService simpleService;

    public ReqResClr(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0;i<10;i++){
            System.out.println(simpleService.getDemoData());
            Thread.sleep(500);
        }

    }
}
