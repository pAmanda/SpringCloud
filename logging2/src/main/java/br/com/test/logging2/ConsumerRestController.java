package br.com.test.logging2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ConsumerRestController {


    @Autowired
    private EurekaClient discoveryClient;
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/test")
    public ControllerBean get() {
        ControllerBean response = this.restTemplate.getForObject(getUrl(), ControllerBean.class);
        System.out.println("Resposta serviço getting:");
        System.out.println(response);
        return response;
    }

    @PostMapping("/test") 
    public ControllerBean post(@RequestBody ControllerBean bean){
        return restTemplate.postForObject(getUrl(), bean, ControllerBean.class);
    }

    @PutMapping("/test")
    public void put(@RequestBody ControllerBean bean) {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        this.restTemplate.put(getUrl() + "{id}", bean, params);
    }

    @DeleteMapping("/test/{id}")	
    public void delete(@PathVariable("id") final Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        restTemplate.delete(getUrl() + "{id}", params);

    }

    @GetMapping("/test/list")
    public List<ControllerBean> list(){
        ResponseEntity<List<ControllerBean>> response = restTemplate.exchange(getUrl() + "list", HttpMethod.GET, null, new ParameterizedTypeReference<List<ControllerBean>>(){});
        return response.getBody();
    }

    public String getUrl() {
        List<InstanceInfo> instance = discoveryClient.getInstancesById("globo.com");
        return instance.get(0).getHomePageUrl();
    }
}
