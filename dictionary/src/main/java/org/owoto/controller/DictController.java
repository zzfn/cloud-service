package org.owoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.owoto.entity.Dict;
import org.owoto.service.DictService;
import org.owoto.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzfn
 * @date 2020-12-31 11:38 上午
 */
@RestController
@RequestMapping("dict")
public class DictController {
    @Autowired
    DictService dictService;

    @ApiOperation("获取字典列表")
    @GetMapping("list")
    public Object getList(String typeCode) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TYPE_CODE", typeCode);
        return ResultUtil.success(dictService.list(queryWrapper));
    }

    @ApiOperation("根据id获取字典")
    @GetMapping("")
    public Object getDict(@RequestParam String id) {
        return ResultUtil.success(dictService.getById(id));
    }
}
