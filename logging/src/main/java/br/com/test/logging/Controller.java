package br.com.test.logging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public ControllerBean get() {
        return new ControllerBean("Hello", "Amanda");
    }

    @PostMapping("/")
    public ControllerBean post(@RequestBody ControllerBean bean) {
        return bean;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ControllerBean> update(@PathVariable("id") Long id, @RequestBody ControllerBean bean) {
         if(id == 1L) {
             return new ResponseEntity<>(bean, HttpStatus.OK);
         }
         return new ResponseEntity<>(bean, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        ControllerBean bean = new ControllerBean();
        if(bean.getId() == id) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/list")
    public List<ControllerBean> list(){
        ControllerBean bean = new ControllerBean("Hello", "world");
        List<ControllerBean> listBean = new ArrayList<>();
        listBean.add(bean);
        return listBean;
    }
}
