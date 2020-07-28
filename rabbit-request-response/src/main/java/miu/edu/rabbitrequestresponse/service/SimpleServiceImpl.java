package miu.edu.rabbitrequestresponse.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleServiceImpl implements SimpleService{

    public String getDemoData(){
        return "umur";
    }

}
