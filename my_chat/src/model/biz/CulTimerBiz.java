package model.biz;

import java.util.TimerTask;

/**
 * 用于实时计算数据传输情况
 * @author KumaHime
 *
 */
public class CulTimerBiz extends TimerTask{
	private int totalData;
	private int culData;
	private int oldData;
	private String how;
	
	/**
	 * 构造函数
	 * @param total 需要传输的数据总量（单位不限制）
	 */
	public CulTimerBiz(int total, String h){
		totalData = total;
		oldData = 0;
		how = h;
	}
	
	/**
	 * 设置当前数据传输的量
	 * @param cul 当前传输的数据总量（单位不限制）
	 */
	public void setCulData(int cul){
		culData += cul;
	}
	
	@Override
	public void run() {
		System.out.println(how + "的数据大小 ：" + culData);
		System.out.println(how + "完成进度 ：" + (double)(culData / totalData) + "%");
		System.out.println(how + "速度大小：" + (culData - oldData));
		oldData = culData;
	}
	
}
