package com.meixin.bid_admin.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Desc： admin用户
 * @Author： yanghm
 * @Date： 09:37 2018/5/14 0014
 */
@Entity
@Table(name = "sys_user")
public class Admin implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    private String name;

    private String role;

    private Timestamp createTime;

    private Timestamp updateTime;

    //前端的 竞标单审核 权限
    @Transient
    private Integer checkAuth;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        String[] rol = this.role.split(",");
        for (String r : rol) {
            auths.add(new SimpleGrantedAuthority(r));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    public Integer getCheckAuth() {
        return checkAuth;
    }

    public void setCheckAuth(Integer checkAuth) {
        this.checkAuth = checkAuth;
    }

    /**
     * @Desc:   添加角色
     * @Author: yanghm
     * @Param:
     * @Date:   14:11 2018/6/19 0019
     * @Return:
     */
    public void addRoles(String ...roles) {
        StringBuilder sb = null;
        if (this.role == null) {
            sb = new StringBuilder();
        }else {
            sb = new StringBuilder(this.role);
        }
        if (roles != null) {
            for (String r : roles) {
                sb.append(r).append(",");
            }
        }
        setRole(sb.toString());
    }

}
