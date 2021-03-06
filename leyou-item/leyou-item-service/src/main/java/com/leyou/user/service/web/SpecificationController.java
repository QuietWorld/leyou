package com.leyou.user.service.web;

import com.leyou.item.interf.domain.SpecGroup;
import com.leyou.item.interf.domain.SpecParam;
import com.leyou.item.service.service.SpecificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规格组控制器
 *
 * @author zc
 */
@RestController
@RequestMapping("/spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;
    private static final Logger log = LoggerFactory.getLogger(SpecificationController.class);

    /**
     * 根据商品分类id查询该分类下的规格组
     *
     * @param cid 商品分类id
     * @return
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> listSpecGroupsByCid(@PathVariable("cid") Long cid) {
        if (cid == null) {
            return ResponseEntity.badRequest().build();
        }
        List<SpecGroup> specGroups = specificationService.listSpecGroupsByCid(cid);
        return ResponseEntity.ok(specGroups);
    }

    /**
     * 根据商品分类id查询该分类下的规格组和组内参数
     * @param cid 商品分类id
     * @rturn
     */
    @GetMapping("/list/{cid}")
    public ResponseEntity<List<SpecGroup>> listSpecGroupsAndParamsByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> specGroups = specificationService.listSpecGroupsByCid(cid);
        List<SpecParam> specParams = specificationService.listSpecParams(null, cid, null);
        // 规格组和组内参数进行对应
        // 先将规格参数转换成Map集合，key：规格参数所属的规格组id ， value：规格参数集合
        Map<Long, List<SpecParam>> map = new HashMap<>();
        for (SpecParam specParam : specParams){
            // 判断map集合中是否已经存在当前规格组id的规格参数集合
            if (!map.containsKey(specParam.getGroupId())){
                map.put(specParam.getGroupId(), new ArrayList<>());
            }
            map.get(specParam.getGroupId()).add(specParam);
        }
        for (SpecGroup specGroup : specGroups){
            specGroup.setParams(map.get(specGroup.getId()));
        }
        return ResponseEntity.ok(specGroups);
    }

    /**
     * 查询规格参数集合
     *
     * @param gid 规格组id
     * @param cid 分类id
     * @param searching 该参数是否用于搜索过滤
     * @return
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> listSpecParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching) {
        List<SpecParam> specParams = specificationService.listSpecParams(gid, cid, searching);
        return ResponseEntity.ok(specParams);
    }

    /**
     * 保存规格组
     *
     * @param specGroup
     * @return
     */
    @PostMapping("/group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroup specGroup) {
        log.info("save:{}", specGroup.toString());
        specificationService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新规格组
     *
     * @param specGroup
     * @return
     */
    @PutMapping("/group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup) {
        log.info("update:{}", specGroup.toString());
        specificationService.updateSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除指定id的规格组
     *
     * @param id 规格组的id
     * @return
     */
    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteSpecGroupById(@PathVariable("id") Long id) {
        log.info("delete:{}", id);
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        specificationService.deleteSpecGroupById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 保存规格参数
     *
     * @param specParam
     * @return
     */
    @PostMapping("/param")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParam specParam) {
        specificationService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新规格参数
     *
     * @param specParam
     * @return
     */
    @PutMapping("/param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam) {
        specificationService.updateSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除指定id的规格组内参数
     *
     * @param id 规格组内参数的id
     * @return
     */
    @DeleteMapping("/param/{id}")
    public ResponseEntity<Void> deleteSpecParamById(@PathVariable("id") Long id) {
        specificationService.deleteSpecParamById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
