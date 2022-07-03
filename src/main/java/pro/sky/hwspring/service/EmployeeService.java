package pro.sky.hwspring.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.hwspring.exceptions.EmployeeAlreadyAddedException;
import pro.sky.hwspring.exceptions.EmployeeNotFoundException;
import pro.sky.hwspring.exceptions.EmployeeStorageIsFullException;
import pro.sky.hwspring.exceptions.InvalidInputException;
import pro.sky.hwspring.model.Employee;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class EmployeeService {

    private static final int LIMIT = 10;

    private final Map<String, Employee> employees = new HashMap<>();

    private String getKey(String name, String surname){
        return name+ "|" + surname;
    }

    public Employee add(String name, String surname, int department, double salary) {
        validateInput(name, surname);

        Employee employee = new Employee(name, surname, department, salary);
        String key = getKey(name, surname);
        if (employees.containsKey(key)){
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size()<LIMIT){
            return employees.put(key, employee);
        }
            throw new EmployeeStorageIsFullException();
    }

    public Employee remove(String name, String surname) {
        validateInput(name, surname);

        String key = getKey(name, surname);
        if (!employees.containsKey(key)){
            throw new EmployeeNotFoundException();
        }
        return employees.remove(key);
    }

    public Employee find(String name, String surname) {
        validateInput(name, surname);

        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }

    public List<Employee> getAll(){
        return new ArrayList<>(employees.values());
    }

    public void validateInput(String name, String surname){
        if (!(isAlpha(name) && isAlpha(surname))){
            throw new InvalidInputException();
        }
    }
}