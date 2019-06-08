package com.pinyougou.pojo;

import java.io.Serializable;
import java.util.Date;

public class MgrAdmin  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

    private String password;

    private String phone;

    private String email;

    private String nickName;

    private String name;

    private Date birthday;

    private String headPic;

    private Date lastLoginTime;
    
    
    
    
    

    public MgrAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MgrAdmin(String username, String password, String phone, String email, String nickName, String name,
			Date birthday, String headPic, Date lastLoginTime) {
		super();
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.nickName = nickName;
		this.name = name;
		this.birthday = birthday;
		this.headPic = headPic;
		this.lastLoginTime = lastLoginTime;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic == null ? null : headPic.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	@Override
	public String toString() {
		return "MgrAdmin [username=" + username + ", password=" + password + ", phone=" + phone + ", email=" + email
				+ ", nickName=" + nickName + ", name=" + name + ", birthday=" + birthday + ", headPic=" + headPic
				+ ", lastLoginTime=" + lastLoginTime + "]";
	}
}