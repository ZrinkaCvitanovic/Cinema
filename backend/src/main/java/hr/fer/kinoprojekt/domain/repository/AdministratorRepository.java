package hr.fer.kinoprojekt.domain.repository;

import hr.fer.kinoprojekt.domain.model.Administrator;

import java.util.List;

public interface AdministratorRepository  {
    List<Administrator> getAdministrators();
    Administrator getAdministrator(String ime);
    List<Administrator> filterPoNazivu(String ime);
    void save(Administrator administrator);
    void deletePoNazivu(String ime);
}
