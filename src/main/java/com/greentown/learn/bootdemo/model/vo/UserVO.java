package com.greentown.learn.bootdemo.model.vo;

import java.util.Date;

public class UserVO {
	private String name;

	private String mobile;

	private String type;

	private String identity;

	private String broker;

	private Integer vip;

	private Long houseId;
	
	private Date lastLoginTime;
	
	private Date expLoginTime;

	private boolean special;
	
	private String loginIp;

	private Integer status;

	private String remark; // 备注

	private String brokerMobile; // 置业顾问手机号
	
	private Integer loginStatus;//登录状态 1登录 0未登录

	private Long retainId;
	//
	private Integer preNum;//预选号
	
	private Integer favoriteCnt;
	
	public Integer getFavoriteCnt() {
		return favoriteCnt;
	}

	public void setFavoriteCnt(Integer favoriteCnt) {
		this.favoriteCnt = favoriteCnt;
	}

	/**
	 * @return the expLoginTime
	 */
	public Date getExpLoginTime() {
		return expLoginTime;
	}

	/**
	 * @param expLoginTime the expLoginTime to set
	 */
	public void setExpLoginTime(Date expLoginTime) {
		this.expLoginTime = expLoginTime;
	}

	public Integer getPreNum() {
		return preNum;
	}

	public void setPreNum(Integer preNum) {
		this.preNum = preNum;
	}
	//
	/**
	 * @return the retainId
	 */
	public Long getRetainId() {
		return retainId;
	}

	/**
	 * @param retainId the retainId to set
	 */
	public void setRetainId(Long retainId) {
		this.retainId = retainId;
	}

	/**
	 * @return the special
	 */
	public boolean isSpecial() {
		return special;
	}

	/**
	 * @param special the special to set
	 */
	public void setSpecial(boolean special) {
		this.special = special;
	}

	/**
	 * @return the houseId
	 */
	public Long getHouseId() {
		return houseId;
	}

	/**
	 * @param houseId the houseId to set
	 */
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public Integer getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBrokerMobile() {
		return brokerMobile;
	}

	public void setBrokerMobile(String brokerMobile) {
		this.brokerMobile = brokerMobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
