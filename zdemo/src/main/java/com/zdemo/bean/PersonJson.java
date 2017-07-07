package com.zdemo.bean;

import com.zbase.bean.BaseJson;
import com.zbase.interfaces.IPullToRefreshResponse;

import java.util.List;

public class PersonJson extends BaseJson implements IPullToRefreshResponse<Person> {
	private List<Person> personList;

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

    @Override
    public List<Person> getList() {
        return personList;
    }

//	@Override
//	public String getLastId() {
//		return getList().get(getList().size()).getName();
//	}

}
