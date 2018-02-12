package po;

import java.util.Collection;

public class Authority {
    private Long id;

    private String url;

    Role role;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}


}