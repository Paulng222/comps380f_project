package hkmu.comps380f.dao;

import hkmu.comps380f.model.OnlineUser;
import java.util.List;

public interface OnlineUserRepository {

    public void save(OnlineUser user);

    public List<OnlineUser> findAll();

    public List<OnlineUser> findById(String username);

    public void delete(String username);

}
