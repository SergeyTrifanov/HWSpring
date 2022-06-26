package pro.sky.hwspring.service;

import org.springframework.stereotype.Service;
import pro.sky.hwspring.exceptions.EmployeeAlreadyAddedException;
import pro.sky.hwspring.exceptions.EmployeeNotFoundException;
import pro.sky.hwspring.exceptions.EmployeeStorageIsFullException;
import pro.sky.hwspring.model.Employee;

import java.util.*;

@Service
public class EmployeeService {

    private static final int LIMIT = 10;

    private final Map<String, Employee> employees = new HashMap<>();

    private String getKey(String name, String surname){
        return name+ "|" + surname;
    }

    public Employee add(String name, String surname, int department, double salary) {
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
        String key = getKey(name, surname);
        if (!employees.containsKey(key)){
            throw new EmployeeNotFoundException();
        }
        return employees.remove(key);
    }

    public Employee find(String name, String surname) {
        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }

    public List<Employee> getAll(){
        return new ArrayList<>(employees.values());
    }
}