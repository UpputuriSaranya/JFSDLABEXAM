package com.klef.jfsd.exam;


import java.util.List;
import java.util.Scanner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

public class ClientDemo {
    public static void main(String[] args) {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("Hibernate.cfg.xml").build();
        Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory sf = md.getSessionFactoryBuilder().build();
        Session s = sf.openSession();
        Transaction t;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Insert Student");
            System.out.println("2. Fetch Student by ID");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. HQL: Display all student records with all columns");
            System.out.println("6. HQL: Display all student records with specific columns");
            System.out.println("7. HQL: Display names of students with CGPA greater than 7");
            System.out.println("8. HQL: Delete a student by ID using parameter");
            System.out.println("9. HQL: Update student details by ID using parameter");
            System.out.println("10. HQL: Aggregate functions on CGPA column");
            System.out.println("11. HCQL: Display specific columns from student records");
            System.out.println("12. HCQL: Get 5th to 10th records");
            System.out.println("13. HCQL: Apply various comparisons on CGPA column");
            System.out.println("14. HCQL: Get records ordered by Student Name");
            System.out.println("15. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    while (true) {
                        Client student = new Client();
                        System.out.print("Enter Name: ");
                        student.setName(sc.next());
                        System.out.print("Enter Gender: ");
                        student.setGender(sc.next());
                        System.out.print("Enter Department: ");
                        student.setDepartment(sc.next());
                        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                        student.setDob(sc.next());
                        System.out.print("Enter Contact Number: ");
                        student.setContactnumber(sc.nextLong());
                        System.out.print("Enter CGPA: ");
                        student.setCgpa(sc.nextDouble());
                        System.out.print("Enter Number of Backlogs: ");
                        student.setNob(sc.nextInt());

                        t = s.beginTransaction();
                        s.save(student);
                        t.commit();
                        System.out.println("Inserted Data");

                        System.out.print("Do you want to insert another student? (yes/no): ");
                        String insertMore = sc.next();
                        if (!insertMore.equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    Long id = sc.nextLong();
                    Client fetchedStudent = s.get(Client.class, id);
                    if (fetchedStudent != null) {
                        System.out.println("ID: " + fetchedStudent.getId());
                        System.out.println("Name: " + fetchedStudent.getName());
                        System.out.println("Gender: " + fetchedStudent.getGender());
                        System.out.println("Department: " + fetchedStudent.getDepartment());
                        System.out.println("DOB: " + fetchedStudent.getDob());
                        System.out.println("Contact Number: " + fetchedStudent.getContactnumber());
                        System.out.println("CGPA: " + fetchedStudent.getCgpa());
                        System.out.println("Number of Backlogs: " + fetchedStudent.getNob());
                    } else {
                        System.out.println("Student not found");
                    }
                    break;

                case 3:
                    System.out.print("Enter Student ID to update: ");
                    Long updateId = sc.nextLong();
                    Client updateStudent = s.get(Client.class, updateId);
                    if (updateStudent != null) {
                        System.out.print("Enter new Name: ");
                        updateStudent.setName(sc.next());
                        System.out.print("Enter new Gender: ");
                        updateStudent.setGender(sc.next());
                        System.out.print("Enter new Department: ");
                        updateStudent.setDepartment(sc.next());
                        System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
                        updateStudent.setDob(sc.next());
                        System.out.print("Enter new Contact Number: ");
                        updateStudent.setContactnumber(sc.nextLong());
                        System.out.print("Enter new CGPA: ");
                        updateStudent.setCgpa(sc.nextDouble());
                        System.out.print("Enter new Number of Backlogs: ");
                        updateStudent.setNob(sc.nextInt());

                        t = s.beginTransaction();
                        s.update(updateStudent);
                        t.commit();
                        System.out.println("Updated Data");
                    } else {
                        System.out.println("Student not found");
                    }
                    break;

                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    Long deleteId = sc.nextLong();
                    Client deleteStudent = s.get(Client.class, deleteId);
                    if (deleteStudent != null) {
                        t = s.beginTransaction();
                        s.delete(deleteStudent);
                        t.commit();
                        System.out.println("Deleted Data");
                    } else {
                        System.out.println("Student not found");
                    }
                    break;

                case 5:
                    // HQL: Display all student records with all columns
                    List<Client> studentsAllColumns = s.createQuery("from Student", Client.class).list();
                    for (Client student : studentsAllColumns) {
                        System.out.println(student);
                    }
                    break;

                case 6:
                    // HQL: Display all student records with specific columns
                    List<Object[]> studentsSpecificColumns = s.createQuery("select id, name, cgpa from Student", Object[].class).list();
                    for (Object[] student : studentsSpecificColumns) {
                        System.out.println("ID: " + student[0] + ", Name: " + student[1] + ", CGPA: " + student[2]);
                    }
                    break;

                case 7:
                    // HQL: Display names of students with CGPA greater than 7
                    List<String> studentNames = s.createQuery("select name from Student where cgpa > 7", String.class).list();
                    for (String name : studentNames) {
                        System.out.println("Name: " + name);
                    }
                    break;

                case 8:
                    // HQL: Delete a student by ID using parameter
                    System.out.print("Enter Student ID to delete using parameter: ");
                    Long deleteIdParam = sc.nextLong();
                    t = s.beginTransaction();
                    Query deleteQueryParam = s.createQuery("delete from Student where id = :id");
                    deleteQueryParam.setParameter("id", deleteIdParam);
                    int resultDeleteParam = deleteQueryParam.executeUpdate();
                    t.commit();
                    System.out.println("Number of records deleted: " + resultDeleteParam);
                    break;

                case 9:
                    // HQL: Update student details by ID using parameter
                    System.out.print("Enter Student ID to update using parameter: ");
                    Long updateIdParam = sc.nextLong();
                    t = s.beginTransaction();
                    Query updateQueryParam = s.createQuery("update Student set name = :name, cgpa = :cgpa where id = :id");
                    System.out.print("Enter new Name: ");
                    updateQueryParam.setParameter("name", sc.next());
                    System.out.print("Enter new CGPA: ");
                    updateQueryParam.setParameter("cgpa", sc.nextDouble());
                    updateQueryParam.setParameter("id", updateIdParam);
                    int resultUpdateParam = updateQueryParam.executeUpdate();
                    t.commit();
                    System.out.println("Number of records updated: " + resultUpdateParam);
                    break;

                case 10:
                    // HQL: Aggregate functions on CGPA column
                    Query<Object[]> aggregateQuery = s.createQuery("select count(*), sum(cgpa), avg(cgpa), min(cgpa), max(cgpa) from Student", Object[].class);
                    Object[] aggregateResults = aggregateQuery.uniqueResult();
                    System.out.println("Count: " + aggregateResults[0] + ", Sum: " + aggregateResults[1] + ", Avg: " + aggregateResults[2] + ", Min: " + aggregateResults[3] + ", Max: " + aggregateResults[4]);
                    break;

                case 11:
                    // HCQL: Display specific columns from student records
                    Criteria criteriaSpecificColumns = s.createCriteria(Client.class)
                            .setProjection(Projections.projectionList()
                                    .add(Projections.property("id"))
                                    .add(Projections.property("name"))
                                    .add(Projections.property("cgpa")));
                    List<Object[]> specificColumnsList = criteriaSpecificColumns.list();
                    for (Object[] row : specificColumnsList) {
                        System.out.println("ID: " + row[0] + ", Name: " + row[1] + ", CGPA: " + row[2]);
                    }
                    break;

                case 12:
                    // HCQL: Get 5th to 10th records
                    Criteria criteriaFifthToTenth = s.createCriteria(Client.class)
                            .setFirstResult(4)  // 5th record (index starts from 0)
                            .setMaxResults(6);  // Total 6 records (5th to 10th)
                    List<Client> fifthToTenthRecords = criteriaFifthToTenth.list();
                    for (Client student : fifthToTenthRecords) {
                        System.out.println(student);
                    }
                    break;

                case 13:
                    // HCQL: Apply various comparisons on CGPA column
             
                    // HCQL: Apply various comparisons on CGPA column using CriteriaQuery
               
                    CriteriaBuilder cb = s.getCriteriaBuilder();
                    CriteriaQuery<Client> cq = cb.createQuery(Client.class);
                    Root<Client> root = cq.from(Client.class);

                    // Start with a single condition (e.g., cgpa > 7.0)
                    Predicate predicate = cb.greaterThan(root.get("cgpa"), 9.0);
                    
                    
                    cq.where(predicate);
                    
                    List<Client> comparisonResults = s.createQuery(cq).getResultList();
                    for (Client student : comparisonResults) {
                        System.out.println(student);
                    }
                    break;



                case 14:
                    // HCQL: Get records ordered by Student Name
                    Criteria criteriaOrder = s.createCriteria(Client.class)
                            .addOrder(Order.asc("name"));  // Ascending order
                    List<Client> ascendingOrderList = criteriaOrder.list();
                    for (Client student : ascendingOrderList) {
                        System.out.println(student);
                    }

                    criteriaOrder = s.createCriteria(Client.class)
                            .addOrder(Order.desc("name"));  // Descending order
                    List<Client> descendingOrderList = criteriaOrder.list();
                    for (Client student : descendingOrderList) {
                        System.out.println(student);
                    }
                    break;

                case 15:
                    System.out.println("Exiting...");
                    sc.close();
                    s.close();
                    sf.close();
                    return;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}
