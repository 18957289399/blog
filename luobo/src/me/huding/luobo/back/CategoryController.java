/**
 * Copyright (c) 2015-2016, Silly Boy 胡建洪(1043244432@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.huding.luobo.back;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import me.huding.luobo.ResConsts;
import me.huding.luobo.model.Category;
import me.huding.luobo.utils.DBUtils;
import me.huding.luobo.utils.DateUtils;
import me.huding.luobo.utils.KeyUtils;

/**
 *
 *
 * @author JianhongHu
 * @version 1.0
 * @date 2016年11月2日
 */
public class CategoryController extends AbstarctBackController {

	public void display(){
		List<Category> categories = Category.findAll();
		render(ResConsts.Code.SUCCESS, null, categories);
	}

	@Override
	public void doPage(int pageNum, int pageSize) {
		// 查询数据
		Page<Record> data = Category.paginate(pageNum, pageSize);
		// 渲染结果
		render(ResConsts.Code.SUCCESS, null, data);
	}

	@Override
	public void add() {
		Category category = getModel(Category.class, "category");
		category.setId(KeyUtils.getUUID());
		category.setCdate(new Date(System.currentTimeMillis()));
		if(category.save()){
			render(ResConsts.Code.SUCCESS,"保存成功");
		} else {
			render(ResConsts.Code.FAILURE,"保存失败");
		}
	}
	
	public void all(){
		List<Category> data = DBUtils.findAll(Category.dao);
		// 渲染结果
		render(ResConsts.Code.SUCCESS, null, data);
	}

	@Override
	protected boolean doDel(String id) {
		return Category.dao.deleteById(id);
	}

	@Override
	protected Object doGet(String id) {
		return Category.dao.findById(id);
	}

	@Override
	public void edit() {
		Category category = getModel(Category.class, "category");
		Category t = Category.dao.findById(category.getId());
		if(t== null){
			render(ResConsts.Code.FAILURE,"记录不存在");
			return;
		}
		t.setName(category.getName());
		t.setTypeID(category.getTypeID());
		String keywords = category.getKeywords();
		t.setKeywords(StrKit.isBlank(keywords) ? "" : keywords);
		if(t.getCdate() == null){
			t.setCdate(DateUtils.getCurrentDate());
		}
		if(t.update()){
			render(ResConsts.Code.SUCCESS,"修改成功");
		} else {
			render(ResConsts.Code.FAILURE,"修改失败");
		}
	}
}
