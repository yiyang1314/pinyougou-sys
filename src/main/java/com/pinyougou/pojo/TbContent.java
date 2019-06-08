package com.pinyougou.pojo;

import java.io.Serializable;

public class TbContent implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long categoryId;

    private String title;

    private String url;

    private String pic;

    private String status;

    private Integer sortOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

	public TbContent(Long id, Long categoryId, String title, String url, String pic, String status, Integer sortOrder) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.title = title;
		this.url = url;
		this.pic = pic;
		this.status = status;
		this.sortOrder = sortOrder;
	}

	public TbContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TbContent [id=" + id + ", categoryId=" + categoryId + ", title=" + title + ", url=" + url + ", pic="
				+ pic + ", status=" + status + ", sortOrder=" + sortOrder + "]";
	}
    
    
    
}