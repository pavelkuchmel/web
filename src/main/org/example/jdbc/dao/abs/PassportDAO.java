package main.org.example.jdbc.dao.abs;

import main.org.example.model.Passport;

import java.util.List;
import java.util.Set;

public interface PassportDAO {
    boolean createPassport(Passport passport);

    Passport findById(int id);
    Passport findByIndId(String indId);
    boolean deleteById(int id);

    boolean updatePassport(Passport passport);

    List<Passport> all();
}
