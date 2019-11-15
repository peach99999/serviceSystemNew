package cn.jantd;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @Description
 * @Author 圈哥
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/11/9
 */

@RestController
public class ServiceController {

    // Save
    @PostMapping("/v1/services/register")
    @ResponseStatus(HttpStatus.CREATED)
    JSONObject register(@RequestBody JSONObject jsonpObject) {
     return jsonpObject ;
    }


    // Find
    @GetMapping("/v1/services")
    JSONArray  findAll() {
        return new JSONArray();
    }

    // Find
    @GetMapping("/services/{id}")
    JSONObject findOne(@PathVariable Long id) {
       return  new JSONObject();
    }

    // Save or update
    @PutMapping("/services/{id}")
    JSONObject saveOrUpdate(@RequestBody JSONObject jsonpObject, @PathVariable Long id) {
        return  new JSONObject();
    }

    // update author only
    @PatchMapping("/services/{id}")
    JSONObject patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return  new JSONObject();
    }

    @DeleteMapping("/services/{id}")
    void deleteBook(@PathVariable Long id) {

    }




}
