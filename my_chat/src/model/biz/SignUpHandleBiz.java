package model.biz;

import javax.swing.JOptionPane;

import comondata.comondata;

public class SignUpHandleBiz {
	private static SignUpHandleBiz signUpHandleBiz = new SignUpHandleBiz();

	public static SignUpHandleBiz getSignUpHandleBiz() {
		return signUpHandleBiz;
	}

	public void signUp(int id) {
		if (id == -1) {
			JOptionPane.showMessageDialog(comondata.getSignUp(), "³ö´íÀ²");
			return;
		}
		JOptionPane.showMessageDialog(comondata.getSignUp(), "ÄúµÄÕËºÅÎª£º" + String.valueOf(id));
		comondata.getSignUp().dispose();
		comondata.setSignUp(null);
	}
}
