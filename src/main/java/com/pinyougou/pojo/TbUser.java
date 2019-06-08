package com.pinyougou.pojo;

import java.io.Serializable;
import java.util.Date;

public class TbUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1314118105284452534L;
	private Long id;
    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    private String sourceType;

    private String nickName;

    private String name;

    private String status;

    private String headPic;

    private String qq;

    private Long accountBalance;

    private String isMobileCheck;

    private String isEmailCheck;

    private String sex;

    private Integer userLevel;

    private Integer points;

    private Integer experienceValue;

    private Date birthday;

    private Date lastLoginTime;

    public TbUser(String username, String password, String sourceType, String nickName, String name, String status,
			String headPic, String isMobileCheck, String isEmailCheck, String sex, Integer userLevel,
			Integer experienceValue, Date birthday, Date lastLoginTime) {
		super();
		this.username = username;
		this.password = password;
		this.sourceType = sourceType;
		this.nickName = nickName;
		this.name = name;
		this.status = status;
		this.headPic = headPic;
		this.isMobileCheck = isMobileCheck;
		this.isEmailCheck = isEmailCheck;
		this.sex = sex;
		this.userLevel = userLevel;
		this.experienceValue = experienceValue;
		this.birthday = birthday;
		this.lastLoginTime = lastLoginTime;
	}

	public TbUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TbUser(Long id, String username, String password, String nickName, String name, String status,
			String headPic, String sex) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickName = nickName;
		this.name = name;
		this.status = status;
		this.headPic = headPic;
		this.sex = sex;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic == null ? null : headPic.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public Long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getIsMobileCheck() {
        return isMobileCheck;
    }

    public void setIsMobileCheck(String isMobileCheck) {
        this.isMobileCheck = isMobileCheck == null ? null : isMobileCheck.trim();
    }

    public String getIsEmailCheck() {
        return isEmailCheck;
    }

    public void setIsEmailCheck(String isEmailCheck) {
        this.isEmailCheck = isEmailCheck == null ? null : isEmailCheck.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getExperienceValue() {
        return experienceValue;
    }

    public void setExperienceValue(Integer experienceValue) {
        this.experienceValue = experienceValue;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	@Override
	public String toString() {
		return "TbUser [id=" + id + ", username=" + username + ", password=" + password + ", nickName=" + nickName
				+ ", name=" + name + ", headPic=" + headPic + ", sex=" + sex + ", userLevel=" + userLevel
				+ ", lastLoginTime=" + lastLoginTime + "]";
	}
    
    
    
}