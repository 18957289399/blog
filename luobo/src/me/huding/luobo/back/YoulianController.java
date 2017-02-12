package me.huding.luobo.back;

import java.util.Date;

import com.jfinal.i18n.Res;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import me.huding.luobo.ResConsts;
import me.huding.luobo.model.Youlian;

public class YoulianController extends AbstarctBackController {

	@Override
	public void doPage(int pageNum, int pageSize) {
		// 查询数据
		Page<Record> data = Youlian.paginate(pageNum, pageSize);
		// 渲染结果
		render(ResConsts.Code.SUCCESS, null, data);
	}

	@Override
	public void add() {
		Youlian youlian = getModel(Youlian.class, "youlian");
		youlian.setCdate(new Date(System.currentTimeMillis()));
		if(youlian.save()){
			render(ResConsts.Code.SUCCESS,"保存成功");
		} else {
			render(ResConsts.Code.FAILURE,"保存失败");
		}
	}

	@Override
	protected boolean doDel(String id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void edit() {
		// TODO Auto-generated method stub
		
	}
	
	
	protected Object doGet(String id){
		return Youlian.dao.findById(id);
	}
	
	
	

}
