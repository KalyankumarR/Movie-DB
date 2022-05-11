import java.sql.*;

public class Movieee {
    private Connection connect() //connect function used to create a connection
    {
        Connection c=null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            c=DriverManager.getConnection("jdbc:sqlite:movies.db");
            c.setAutoCommit(false);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public void createTable() //createTable is used to create a table in the database
    {
        String cr="CREATE TABLE MOVIES (MOVID INT PRIMARY KEY NOT NULL,"+"NAME TEXT NOT NULL,"+"ACTOR TEXT NOT NULL,"+"ACTRESS TEXT NOT NULL,"+"YEAR CHAR(4),"+"DIRNAME TEXT)";
        try
        {
            Connection c = this.connect();
            Statement stmt =c.createStatement();
            stmt.execute(cr);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void insert(int movid,String name,String actor,String actress,String year,String dir_name) //insert function is used to insert the values into the table movies
    {
        String insr ="INSERT INTO MOVIES(MOVID,NAME,ACTOR,ACTRESS,YEAR,DIRNAME) VALUES(?,?,?,?,?,?)";
        try
        {
            Connection c=this.connect();

            PreparedStatement pstmt=c.prepareStatement(insr);
            pstmt.setInt(1,movid);
            pstmt.setString(2,name);
            pstmt.setString(3,actor);
            pstmt.setString(4,actress);
            pstmt.setString(5,year);
            pstmt.setString(6,dir_name);
            pstmt.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void select()
    {

        try
        {
            Connection c =this.connect();
            Statement stmt = c.createStatement();
            ResultSet rs= stmt.executeQuery("SELECT * FROM MOVIES;");
            while(rs.next())
            {
                int id=rs.getInt("MOVID");
                String movie_name=rs.getString("NAME");
                String actor=rs.getString("ACTOR");
                String actress=rs.getString("ACTRESS");
                String year=rs.getString("YEAR");
                String dir_name=rs.getString("DIRNAME");

                System.out.println("MOVIE ID = "+id);
                System.out.println("MOVIE NAME = "+movie_name);
                System.out.println("ACTOR NAME = "+actor);
                System.out.println("ACTRESS NAME = "+actress);
                System.out.println("YEAR = "+year);
                System.out.println("Director Name = "+dir_name);
                System.out.println();
            }
            rs.close();
            stmt.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void selectmov()
    {

        try
        {
            Connection c =this.connect();
            Statement stmt = c.createStatement();
            ResultSet rs= stmt.executeQuery("SELECT NAME FROM MOVIES WHERE ACTOR='Prabhas';");
            System.out.println("The movies in which Prabhas acted are: ");
            while(rs.next())
            {
                String movie_name=rs.getString("NAME");
                System.out.println(movie_name);
            }
            rs.close();
            stmt.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) {
        Movies a =new Movies(); //creating an object of the class Movies
        a.createTable();
        a.insert(10,"Magadhira","Ramcharan","Kajal","2009","RAJAMOULI");
        a.insert(11,"BAAHUBALI","PRABHAS","ANUSHKA","2015","RAJAMOULI");
        a.insert(12,"The Pursuit of Happyness","Will Smith","Thandiwe Newton","2006","Gabriele Muccino");
        a.insert(13,"Bahubali 2","Prabhas","Anushka","2017","Rajamouli");
        a.insert(14,"KGF Chapter 2","Yash","Shrinidhi Shetty","2022","Prashanth Neel");
        a.insert(15,"24","Surya","Nithya Menon","2017","Vikram Kumar");
        a.insert(16,"Nanaku Prematho","Jr.NTR","Rakul Preeth Singh","2016","Sukumar");
        a.select();
        a.selectmov();
    }
}
