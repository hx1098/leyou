package com.leyou.item.web;

import com.leyou.item.service.SpecificationService;
import com.leyouo.item.pojo.SpecGroup;
import com.leyouo.item.pojo.SpecParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/1
 * Time:15:45
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;


    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid")Long cid){
        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));
    }

    /**
     * 添加规格组
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public  ResponseEntity<Void> addSpec(@RequestBody SpecGroup specGroup){
        if(specGroup.getCid()  < 0 ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!StringUtils.isNotBlank(specGroup.getName())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        specificationService.addSpec(specGroup);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据id更新规格组
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> udpSpec(@RequestBody SpecGroup specGroup){
        specificationService.updSpec(specGroup);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据id删除规格组表数据
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> delSpec(@PathVariable("id")String id){
        specificationService.delSpec(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据组id查询参数
     * @param gid 组id
     * @param cid 类型id
     * @param searching 是否是搜索字段
     * @return
     */
    @GetMapping("params")
    public  ResponseEntity<List<SpecParam>> queryParamByList(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid",required = false)Long cid,
            @RequestParam(value = "searching",required = false)Boolean searching){
        return ResponseEntity.ok(specificationService.queryParamList(gid,cid,searching));
    }


    @PostMapping("param")
    public ResponseEntity<Void> addParam(@RequestBody SpecParam specParam){
        specificationService.addParam(specParam);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("param")
    public ResponseEntity<Void> updParam(@RequestBody SpecParam specParam){
        specificationService.updParam(specParam);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> delParam(@PathVariable("id")Long id){
        specificationService.delParam(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据cid查询规格组，以及组内参数
     * @param cid
     * @return
     */
    @GetMapping("group")
    public ResponseEntity<List<SpecGroup>> queryListByCid(@RequestParam("cid")Long cid){
        return  ResponseEntity.ok(specificationService.queryListByCid(cid));
    }

}
