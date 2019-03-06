package com.jl.jlapp.eneity;

import java.util.List;

/**
 * 根据商品信息列表获取活动列表和优惠券列表 参数model
 * @author 景雅倩
 *
 */
public class ParamForGetActivitysAndCouponsByGoodsMsg {


	/**
	 * userId : 1
	 * goodsMsgList : [{"goodsActivityList":[{"id":1,"identifier":"sddas","name":"sddas","activityType":1,"price":5.5,"discount":3.2,"maxNum":1,"introduction":"sddas","messagePicUrl":"sddas","showPicUrl":"sddas","budget":2.2,"beginValidityTime":123456789456123456,"endValidityTime":123456789456123456,"state":1,"operatorIdentifier":"aa","operatorTime":123456789456789456}],"goodsSpeActivityList":[{"id":1,"identifier":"sddas","name":"sddas","activityType":1,"price":5.5,"discount":3.2,"maxNum":1,"introduction":"sddas","messagePicUrl":"sddas","showPicUrl":"sddas","budget":2.2,"beginValidityTime":123456789456123456,"endValidityTime":123456789456123456,"state":1,"operatorIdentifier":"aa","operatorTime":123456789456789456}],"number":1,"unitPrice":5.5,"goodsSpeId":1}]
	 */

	private int userId;
	private List<GoodsMsgListBean> goodsMsgList;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<GoodsMsgListBean> getGoodsMsgList() {
		return goodsMsgList;
	}

	public void setGoodsMsgList(List<GoodsMsgListBean> goodsMsgList) {
		this.goodsMsgList = goodsMsgList;
	}

	public static class GoodsMsgListBean {
		/**
		 * goodsActivityList : [{"id":1,"identifier":"sddas","name":"sddas","activityType":1,"price":5.5,"discount":3.2,"maxNum":1,"introduction":"sddas","messagePicUrl":"sddas","showPicUrl":"sddas","budget":2.2,"beginValidityTime":123456789456123456,"endValidityTime":123456789456123456,"state":1,"operatorIdentifier":"aa","operatorTime":123456789456789456}]
		 * goodsSpeActivityList : [{"id":1,"identifier":"sddas","name":"sddas","activityType":1,"price":5.5,"discount":3.2,"maxNum":1,"introduction":"sddas","messagePicUrl":"sddas","showPicUrl":"sddas","budget":2.2,"beginValidityTime":123456789456123456,"endValidityTime":123456789456123456,"state":1,"operatorIdentifier":"aa","operatorTime":123456789456789456}]
		 * number : 1
		 * unitPrice : 5.5
		 * goodsSpeId : 1
		 */

		private int number;
		private double unitPrice;
		private int goodsSpeId;
		private List<GoodsActivityListBean> goodsActivityList;
		private List<GoodsSpeActivityListBean> goodsSpeActivityList;

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public double getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(double unitPrice) {
			this.unitPrice = unitPrice;
		}

		public int getGoodsSpeId() {
			return goodsSpeId;
		}

		public void setGoodsSpeId(int goodsSpeId) {
			this.goodsSpeId = goodsSpeId;
		}

		public List<GoodsActivityListBean> getGoodsActivityList() {
			return goodsActivityList;
		}

		public void setGoodsActivityList(List<GoodsActivityListBean> goodsActivityList) {
			this.goodsActivityList = goodsActivityList;
		}

		public List<GoodsSpeActivityListBean> getGoodsSpeActivityList() {
			return goodsSpeActivityList;
		}

		public void setGoodsSpeActivityList(List<GoodsSpeActivityListBean> goodsSpeActivityList) {
			this.goodsSpeActivityList = goodsSpeActivityList;
		}

		public static class GoodsActivityListBean {
			/**
			 * id : 1
			 * identifier : sddas
			 * name : sddas
			 * activityType : 1
			 * price : 5.5
			 * discount : 3.2
			 * maxNum : 1
			 * introduction : sddas
			 * messagePicUrl : sddas
			 * showPicUrl : sddas
			 * budget : 2.2
			 * beginValidityTime : 123456789456123456
			 * endValidityTime : 123456789456123456
			 * state : 1
			 * operatorIdentifier : aa
			 * operatorTime : 123456789456789456
			 */

			private int id;
			private String identifier;
			private String name;
			private int activityType;
			private double price;
			private double discount;
			private int maxNum;
			private String introduction;
			private String messagePicUrl;
			private String showPicUrl;
			private double budget;
			private long beginValidityTime;
			private long endValidityTime;
			private int state;
			private String operatorIdentifier;
			private long operatorTime;

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getIdentifier() {
				return identifier;
			}

			public void setIdentifier(String identifier) {
				this.identifier = identifier;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public int getActivityType() {
				return activityType;
			}

			public void setActivityType(int activityType) {
				this.activityType = activityType;
			}

			public double getPrice() {
				return price;
			}

			public void setPrice(double price) {
				this.price = price;
			}

			public double getDiscount() {
				return discount;
			}

			public void setDiscount(double discount) {
				this.discount = discount;
			}

			public int getMaxNum() {
				return maxNum;
			}

			public void setMaxNum(int maxNum) {
				this.maxNum = maxNum;
			}

			public String getIntroduction() {
				return introduction;
			}

			public void setIntroduction(String introduction) {
				this.introduction = introduction;
			}

			public String getMessagePicUrl() {
				return messagePicUrl;
			}

			public void setMessagePicUrl(String messagePicUrl) {
				this.messagePicUrl = messagePicUrl;
			}

			public String getShowPicUrl() {
				return showPicUrl;
			}

			public void setShowPicUrl(String showPicUrl) {
				this.showPicUrl = showPicUrl;
			}

			public double getBudget() {
				return budget;
			}

			public void setBudget(double budget) {
				this.budget = budget;
			}

			public long getBeginValidityTime() {
				return beginValidityTime;
			}

			public void setBeginValidityTime(long beginValidityTime) {
				this.beginValidityTime = beginValidityTime;
			}

			public long getEndValidityTime() {
				return endValidityTime;
			}

			public void setEndValidityTime(long endValidityTime) {
				this.endValidityTime = endValidityTime;
			}

			public int getState() {
				return state;
			}

			public void setState(int state) {
				this.state = state;
			}

			public String getOperatorIdentifier() {
				return operatorIdentifier;
			}

			public void setOperatorIdentifier(String operatorIdentifier) {
				this.operatorIdentifier = operatorIdentifier;
			}

			public long getOperatorTime() {
				return operatorTime;
			}

			public void setOperatorTime(long operatorTime) {
				this.operatorTime = operatorTime;
			}
		}

		public static class GoodsSpeActivityListBean {
			/**
			 * id : 1
			 * identifier : sddas
			 * name : sddas
			 * activityType : 1
			 * price : 5.5
			 * discount : 3.2
			 * maxNum : 1
			 * introduction : sddas
			 * messagePicUrl : sddas
			 * showPicUrl : sddas
			 * budget : 2.2
			 * beginValidityTime : 123456789456123456
			 * endValidityTime : 123456789456123456
			 * state : 1
			 * operatorIdentifier : aa
			 * operatorTime : 123456789456789456
			 */

			private int id;
			private String identifier;
			private String name;
			private int activityType;
			private double price;
			private double discount;
			private int maxNum;
			private String introduction;
			private String messagePicUrl;
			private String showPicUrl;
			private double budget;
			private long beginValidityTime;
			private long endValidityTime;
			private int state;
			private String operatorIdentifier;
			private long operatorTime;

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getIdentifier() {
				return identifier;
			}

			public void setIdentifier(String identifier) {
				this.identifier = identifier;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public int getActivityType() {
				return activityType;
			}

			public void setActivityType(int activityType) {
				this.activityType = activityType;
			}

			public double getPrice() {
				return price;
			}

			public void setPrice(double price) {
				this.price = price;
			}

			public double getDiscount() {
				return discount;
			}

			public void setDiscount(double discount) {
				this.discount = discount;
			}

			public int getMaxNum() {
				return maxNum;
			}

			public void setMaxNum(int maxNum) {
				this.maxNum = maxNum;
			}

			public String getIntroduction() {
				return introduction;
			}

			public void setIntroduction(String introduction) {
				this.introduction = introduction;
			}

			public String getMessagePicUrl() {
				return messagePicUrl;
			}

			public void setMessagePicUrl(String messagePicUrl) {
				this.messagePicUrl = messagePicUrl;
			}

			public String getShowPicUrl() {
				return showPicUrl;
			}

			public void setShowPicUrl(String showPicUrl) {
				this.showPicUrl = showPicUrl;
			}

			public double getBudget() {
				return budget;
			}

			public void setBudget(double budget) {
				this.budget = budget;
			}

			public long getBeginValidityTime() {
				return beginValidityTime;
			}

			public void setBeginValidityTime(long beginValidityTime) {
				this.beginValidityTime = beginValidityTime;
			}

			public long getEndValidityTime() {
				return endValidityTime;
			}

			public void setEndValidityTime(long endValidityTime) {
				this.endValidityTime = endValidityTime;
			}

			public int getState() {
				return state;
			}

			public void setState(int state) {
				this.state = state;
			}

			public String getOperatorIdentifier() {
				return operatorIdentifier;
			}

			public void setOperatorIdentifier(String operatorIdentifier) {
				this.operatorIdentifier = operatorIdentifier;
			}

			public long getOperatorTime() {
				return operatorTime;
			}

			public void setOperatorTime(long operatorTime) {
				this.operatorTime = operatorTime;
			}
		}
	}
}
