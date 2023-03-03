package com.basilisk.utility;
import java.util.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Dropdown {
    @Getter @Setter private String stringValue;
    @Getter @Setter private Long longValue;
    @Getter @Setter private String text;

    public Dropdown(Long longValue, String text) {
        this.longValue = longValue;
        this.text = text;
    }

    public Dropdown(String stringValue, String text) {
        this.stringValue = stringValue;
        this.text = text;
    }

    public static List<Dropdown> getEmployeeLevelDropdown(){
        List<Dropdown> employeeLevel = new LinkedList<Dropdown>();
        employeeLevel.add(new Dropdown("National_Sales_Director", "National_Sales_Director"));
        employeeLevel.add(new Dropdown("Regional_Sales_Director", "Regional_Sales_Director"));
        employeeLevel.add(new Dropdown("Sales_Manager", "Sales_Manager"));
        employeeLevel.add(new Dropdown("Inside_Sales_Manager", "Inside_Sales_Manager"));
        employeeLevel.add(new Dropdown("Outside_Sales_Manager", "Outside_Sales_Manager"));
        employeeLevel.add(new Dropdown("Sales_Assistant", "Sales_Assistant"));
        employeeLevel.add(new Dropdown("Sales_Engineer", "Sales_Engineer"));
        employeeLevel.add(new Dropdown("Wholesale_Sales", "Wholesale_Sales"));
        employeeLevel.add(new Dropdown("Retail_Sales", "Retail_Sales"));
        return employeeLevel;
    }

    public static List<Dropdown> getRoleDropdown(){
        List<Dropdown> roles = new LinkedList<Dropdown>();
        roles.add(new Dropdown("Salesman", "Salesman"));
        roles.add(new Dropdown("Finance", "Finance"));
        roles.add(new Dropdown("Administrator", "Administrator"));
        return roles;
    }
}
