package com.myf.qq.service.impl;

import java.util.TimerTask;

/**
 * ����ʵʱ������ݴ������
 * @author KumaHime
 *
 */
public class CulTimerBiz extends TimerTask{
	private int totalData;
	private int culData;
	private int oldData;
	private String how;
	
	/**
	 * ���캯��
	 * @param total ��Ҫ����������������λ�����ƣ�
	 */
	public CulTimerBiz(int total, String h){
		totalData = total;
		oldData = 0;
		how = h;
	}
	
	/**
	 * ���õ�ǰ��ݴ������
	 * @param cul ��ǰ����������������λ�����ƣ�
	 */
	public void setCulData(int cul){
		culData += cul;
	}
	
	@Override
	public void run() {
		System.out.println(how + "����ݴ�С ��" + culData);
		System.out.println(how + "��ɽ�� ��" + (double)(culData / totalData) + "%");
		System.out.println(how + "�ٶȴ�С��" + (culData - oldData));
		oldData = culData;
	}
	
}
