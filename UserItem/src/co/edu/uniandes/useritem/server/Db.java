package co.edu.uniandes.useritem.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.util.ArrayList;
import java.util.List;

import org.recommender101.data.DataModel;
import org.recommender101.eval.impl.Recommender101Impl;
import org.recommender101.tools.Utilities101;

//STEP 1. Import required packages

public class Db {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/dbuseritem";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "123456";
	
	public static DataModel model= new DataModel();
 
	public static void Init()
	{
		
		model = new DataModel();
		
		Connection conn = null;
		Statement stmt = null;
		
		try{
			
			Class.forName(JDBC_DRIVER).newInstance();
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = (Statement) conn.createStatement();
			String sql;
			sql = "select * from rating limit 0, 15000";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				 
				model.addRating(Integer.parseInt(rs.getString("userId")) , Integer.parseInt(rs.getString("movieId")), Integer.parseInt(rs.getString("rating")));
 
			}
						
			stmt.close();
			conn.close();
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}
		
	}
	
	public void save(String data) {
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER).newInstance();
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = (Statement) conn.createStatement();
			String sql;
			String[] input = data.split("\\|");
			sql = "INSERT INTO user (userId, name, twitterId, email) VALUES (" + input[0]
					+ ", '" + input[1] + "', '" + input[2] + "', '" + input[3] + "');";
			stmt.executeUpdate(sql);
			
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	public String[] getStatistics() {
		Connection conn = null;
		Statement stmt = null;
		String[] statisticsValues = new String[13];
		
		model = new DataModel();
		
		try{
			
			Class.forName(JDBC_DRIVER).newInstance();
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = (Statement) conn.createStatement();
			String sql;
			sql = "select * from rating ";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				 
				model.addRating(Integer.parseInt(rs.getString("userId")) , Integer.parseInt(rs.getString("movieId")), Integer.parseInt(rs.getString("rating")));
 
			}
			
			statisticsValues[0] = "Cantidad Users|" + model.getUsers().size();
			statisticsValues[1] = "Cantidad Peliculas| " + model.getItems().size();
			statisticsValues[2] = "Cantidad Ratings|" + model.getRatings().size();
			statisticsValues[3] = "Sparsity|" + Recommender101Impl.decimalFormat.format((double) model.getRatings().size() / ((double) (model.getItems().size() * model.getUsers().size())));

			// Global average
			double globalAverage = Utilities101.getGlobalRatingAverage(model);
			statisticsValues[4] = "Global avg|" + Recommender101Impl.decimalFormat.format(globalAverage);
			
			// Get the median rating
			int median = -1;
			median = Utilities101.getGlobalMedianRating(model);
			statisticsValues[5] = "Global median|" + Recommender101Impl.decimalFormat.format(median);
			
			// Rating statistics
			Map<Integer, Integer> frequencies = Utilities101.getRatingFrequencies(model);
			statisticsValues[6] = "Ratings freqs|" + frequencies;
			
			// Avg ratings per user and item
			statisticsValues[7] = "Avg. Ratings/user|" + Recommender101Impl.decimalFormat.format((double) model.getRatings().size() / model.getUsers().size());
			statisticsValues[8] = "Avg. Ratings/item|" + Recommender101Impl.decimalFormat.format((double)model.getRatings().size() / model.getItems().size());
			statisticsValues[9] = "Min. Ratings/user|" + model.getMinUserRatings();
			statisticsValues[10] = "Min. Ratings/item|" + model.getMinItemRatings();
			statisticsValues[11] = "Max. Ratings/user|" + model.getMaxUserRatings();
			statisticsValues[12] = "Max. Ratings/item|" + model.getMaxItemRatings();
			
			//Map<Integer, Float> ratings = Utilities101.sortByValueDescending(itemAverages);
			
						
			stmt.close();
			conn.close();
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}
		return statisticsValues;

	}
	
	public String getNameMovie(String movieId)
	{
		Connection conn = null;
		Statement stmt = null;
		String nameMovie = "";
		
		try{
			
			Class.forName(JDBC_DRIVER).newInstance();
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = (Statement) conn.createStatement();
			String sql;
			sql = "select tittle from movie where movieId = '" + String.format("%07d", Integer.parseInt(movieId)) + "'"; 
						
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				nameMovie = rs.getString("tittle");
			}
			
			stmt.close();
			conn.close();
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return nameMovie;
	}
	
	public List<String[]> getMoviesMostRatings() {
		Connection conn = null;
		Statement stmt = null;
		List<String[]> ratings = new ArrayList<String[]>();
		
		try{
			
			Class.forName(JDBC_DRIVER).newInstance();
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = (Statement) conn.createStatement();
			String sql;
			sql = "select a.tittle, prom from ( ";
			sql += "select tittle, cuenta*prom, prom from( "; 
			sql += "select tittle, count(1) as cuenta, avg(rating) as prom from ( ";
			sql += "select m.tittle, r.rating ";
			sql += "from rating r ";
			sql += "inner join movie m on r.movieId = m.movieId ";
			sql += "limit 0,15000 ";
			sql += ") b ";
			sql += "group by tittle ";
			sql += ") c ";
			sql += "order by cuenta*prom desc ";
			sql += "limit 0,10 ";
			sql += ") a  ";
			sql += "order by prom desc ";

			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String[] s= new String[2];
				s[0] = rs.getString("tittle");
				s[1] = rs.getString("prom");
				
				ratings.add(s);
 
			}
			
			stmt.close();
			conn.close();
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		return ratings;

	}//end main
	
	public List<String[]> getMoviesByUser(String user) {
		Connection conn = null;
		Statement stmt = null;
		List<String[]> movies = new ArrayList<String[]>();
		
		try{
			
			Class.forName(JDBC_DRIVER).newInstance();
			conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

			stmt = (Statement) conn.createStatement();
			String sql;
			sql = "select m.tittle, r.rating ";
			sql += "from movie m ";
			sql += "inner join rating r on m.movieId = r.movieId ";
			sql += "where r.userId =  " + user;
						
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				String[] s= new String[2];
				s[0] = rs.getString("tittle");
				s[1] = rs.getString("rating");
				
				movies.add(s);
			}
			
			stmt.close();
			conn.close();
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return movies;

	}
	
	
	public static void main(String[] args) {
		new Db().save("35000");
	}
}//end FirstExample