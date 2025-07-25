# RFID_J: RFID-based Security & Attendance System (Simulated)

## Requirements

- Java 11+
- MySQL or PostgreSQL
- JDBC driver for your DB
- [jBCrypt](https://www.mindrot.org/projects/jBCrypt/) library (for password hashing)

### How to install jBCrypt

1. **Download the JAR**  
   Go to [https://mvnrepository.com/artifact/org.mindrot/jbcrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)  
   Click on the latest version, then click the "jar" link to download the file (e.g., `jbcrypt-0.4.jar`).

2. **Place the JAR in your project directory**  
   Move the downloaded `.jar` file into your project folder (e.g., `/home/ninja/Documents/Projects/RFID_J/`).

3. **Add it to your classpath when compiling and running**  
   Example:
   ```
   javac -cp ".:jbcrypt-0.4.jar:path/to/jdbc-driver.jar" */*.java Main.java
   java -cp ".:jbcrypt-0.4.jar:path/to/jdbc-driver.jar" Main
   ```
   Replace `path/to/jdbc-driver.jar` with the actual path to your JDBC driver.

## Setup

1. **Database**
   - Create a database (e.g., `rfid_db`).
   - Run `schema.sql` to create tables and a sample admin.
   - Update `DBConnection.java` with your DB credentials.

2. **Dependencies**
   - Add JDBC driver and jBCrypt to your classpath.

3. **Run**
   - Compile all `.java` files.
   - Run `Main.java`.

## How to Run

1. **Compile the code**  
   Open a terminal in the project directory and run:
   ```
   javac -cp ".:path/to/jbcrypt.jar:path/to/jdbc-driver.jar" */*.java Main.java
   ```
   Replace `path/to/jbcrypt.jar` and `path/to/jdbc-driver.jar` with the actual paths to your jBCrypt and JDBC driver JAR files.

2. **Run the application**  
   ```
   java -cp ".:path/to/jbcrypt.jar:path/to/jdbc-driver.jar" Main
   ```

3. **Login**  
   Use the admin credentials you set up in the database (see `schema.sql`).

4. **Test**  
   To run a test:
   ```
   java -cp ".:path/to/jbcrypt.jar:path/to/jdbc-driver.jar" tests.UserDAOTest
   ```

## Usage
   - Login as admin (default: username `admin`, password as in `schema.sql`).
   - Use dashboard to manage users and attendance.
   - Simulate RFID scan via the GUI.

## Notes

- No real RFID hardware required; use the mock panel.
- For production, secure DB credentials and use environment variables.
- To run tests: `java tests.UserDAOTest`

## Security

- Admin passwords are hashed with BCrypt.
- All SQL uses prepared statements.
- Role-based access enforced in GUI.

## Extending

- Add more panels/tabs for reports, export, etc.
- Integrate real RFID hardware by replacing `MockRFIDPanel`.

## How to set or reset the admin (super user) credentials

- The admin credentials are stored in the `admins` table in your database.
- By default, you must insert an admin user manually using SQL.

### To set the admin username and password to `admin` / `rsdh`:

1. **Generate a BCrypt password hash for `rsdh`**  
   Example hash for password `rsdh` (generated with BCrypt, cost 10):
   ```
   $2a$10$wQwQwQwQwQwQwQwQwQwQeQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQw
   ```
   *(Replace with a real hash for production; this is a placeholder.)*

2. **Insert or update the admin in your database**  
   Connect to your database and run:
   ```sql
   INSERT INTO admins (username, password_hash) VALUES ('admin', '$2a$10$wQwQwQwQwQwQwQwQwQwQeQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQw')
   ON CONFLICT (username) DO UPDATE SET password_hash = EXCLUDED.password_hash;
   ```
   This sets the username to `admin` and password to `rsdh`.

3. **Login**  
   Username: `admin`  
   Password: `rsdh`

## Troubleshooting admin login

If you cannot log in with username `admin` and password `rsdh`, do the following:

1. **Ensure the admin user exists in your database with the correct password hash.**

2. **Generate a real BCrypt hash for `rsdh`:**
   - Save this Java code as `BCryptHash.java`:
     ```java
     import org.mindrot.jbcrypt.BCrypt;
     public class BCryptHash {
         public static void main(String[] args) {
             System.out.println(BCrypt.hashpw("rsdh", BCrypt.gensalt()));
         }
     }
     ```
   - Compile and run it:
     ```
     javac -cp .:jbcrypt-0.4.jar BCryptHash.java
     java -cp .:jbcrypt-0.4.jar BCryptHash
     ```
   - Copy the output hash.

3. **Update your database with the generated hash:**
   - Connect to your database and run:
     ```sql
     DELETE FROM admins WHERE username = 'admin';
     INSERT INTO admins (username, password_hash) VALUES ('admin', '<paste_the_generated_hash_here>');
     ```
   - Replace `<paste_the_generated_hash_here>` with the hash you copied.

4. **Try logging in again with:**
   - Username: `admin`
   - Password: `rsdh`

If you still have issues, check your database connection settings and ensure your application is connecting to the correct database.

## How to connect to your database

1. **Configure your database credentials**

   Open the file:
   ```
   /home/ninja/Documents/Projects/RFID_J/database/DBConnection.java
   ```
   and set the following fields to match your database:
   ```java
   private static final String URL = "jdbc:postgresql://localhost:5432/rfid_db"; // or MySQL URL
   private static final String USER = "your_db_username";
   private static final String PASSWORD = "your_db_password";
   ```
   - For PostgreSQL, use:  
     `jdbc:postgresql://localhost:5432/your_db_name`
   - For MySQL, use:  
     `jdbc:mysql://localhost:3306/your_db_name`

2. **Ensure your database server is running**

   - Start PostgreSQL or MySQL service on your system.
   - Make sure the database (`rfid_db` or your chosen name) exists.

3. **Test the connection**

   - When you run the Java application, it will attempt to connect using these credentials.
   - If there is a connection error, check the URL, username, password, and that the database server is running.

