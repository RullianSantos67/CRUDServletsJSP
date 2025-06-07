package model.dao;

import java.util.HashMap;
import java.util.Map;

import model.dao.MySQLCompanyDAO;
import model.dao.MySQLPetDAO;
import model.dao.MySQLPostDAO;
import model.dao.MySQLUserDAO;

public class DAOFactory {
    
    private static Map<Class<?>, Object> listDAOsInterfaces = new HashMap<Class<?>, Object>();
    
    // Para o DAOFactory funcionar para suas classes de domínio, adicione na 
    // lista suas interfaces e classes DAO na listDAOsInterfaces
    // Se tiver curiosidade, pergunte ao professor sobre o funcionamento de blocos estáticos
    static {
        listDAOsInterfaces.put(PostDAO.class,    new MySQLPostDAO());
        listDAOsInterfaces.put(UserDAO.class,    new MySQLUserDAO());
        listDAOsInterfaces.put(CompanyDAO.class, new MySQLCompanyDAO());
        listDAOsInterfaces.put(PetDAO.class,     new MySQLPetDAO());
    }

    @SuppressWarnings("unchecked")
    public static <DAOInterface> DAOInterface createDAO(Class<?> entity){
        Object dao = listDAOsInterfaces.get(entity);
        if (dao == null) {
            throw new IllegalArgumentException("DAO não suportado: " + entity.getName());
        }
        return (DAOInterface) dao;
    } 
}
