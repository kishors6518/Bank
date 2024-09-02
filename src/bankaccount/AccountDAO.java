package bankaccount;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
	
	public void addAccount(Account acc)
	{
		
		String query="INSERT INTO ACCOUNTS(ACCOUNTNO,ACCOUNT_HOLDER,BALANCE,TIME_STAMP,PIN) VALUES(?,?,?,?,?)";
			try(Connection connection=CreateDBConnection.createConnection();PreparedStatement preparedStatement=connection.prepareStatement(query);) {
				
				preparedStatement.setString(1, acc.getAccountNo());
				preparedStatement.setString(2, acc.getAccount_Holder());
				preparedStatement.setDouble(3, acc.getBalance());
				preparedStatement.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
				preparedStatement.setInt(5, acc.getPin());
				
	
			} catch (SQLException e) {
				System.out.println("Issue in creating the account");
			}
			catch(NullPointerException n) {
				System.out.println("Issue in connection");
			}
	}
	
	public Account getAccount(int id) {
		String query="SELECT * FROM ACCOUNT WHERE ID=?";
		
		try(Connection connection=CreateDBConnection.createConnection();PreparedStatement preparedStatement=connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			
			ResultSet rs=preparedStatement.executeQuery();
			int accId=0;
			String accNo=null;
			String accHolder=null;
			double balance=0;
			int pin=0;
			Timestamp ts=null;
			while(rs.next())
			{
				accId=rs.getInt(1);
				accNo=rs.getString(2);
				accHolder=rs.getString(3);
				balance=rs.getDouble(4);
				ts=rs.getTimestamp(5);
				pin=rs.getInt(6);
			}
			return new Account(accId,accNo,accHolder,balance,ts,pin);
		}
		catch (SQLException e) {
			System.out.println("Issue in fetching the account");
			return null;
		}
		catch (NullPointerException e) {
			return null;
		}
		
	}
	
	public List<Account> getAllAccount()
	{
		String query="SELECT * FROM ACCOUNT";
		List<Account> list=new ArrayList<Account>();
		try(Connection connection=CreateDBConnection.createConnection();PreparedStatement preparedStatement=connection.prepareStatement(query);) {
			
			ResultSet rs=preparedStatement.executeQuery();
			while (rs.next()) {
				list.add(new Account(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getTimestamp(5),rs.getInt(6)));
			}
			
		}
		catch (Exception e) {
			System.out.println("Issue in fetching the accounts");
		}
		return list;
	}
	
	public void deleteAccount(int id)
	{
		String query="DELETE FROM ACCOUNTS WHERE ID=?";
		try(Connection connection=CreateDBConnection.createConnection();PreparedStatement preparedStatement=connection.prepareStatement(query);)
		{
			preparedStatement.setInt(1, id);
			int result=preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("Issue in deleting the account");
		}

	}
	
	public boolean debit(String accountHolder,String accno,int pin,double amt)
	{
		String fetchBalanceQuery="SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_NO=? AND PIN=? AND ACCOUNT_HOLDER=?";
		try(Connection connection=CreateDBConnection.createConnection();PreparedStatement preparedStatement=connection.prepareStatement(fetchBalanceQuery);)
		{
			preparedStatement.setString(1, accno);
			preparedStatement.setInt(2, pin);
			preparedStatement.setString(3, accountHolder);
			ResultSet rs=preparedStatement.executeQuery();
			rs.next();
			
			double balance=rs.getDouble(1);
			if(balance-amt>0)
			{
				String updateBalanceQuery="UPDATE FROM ACCOUNTS SET BALANCE=? WHERE ACCOUNT_NO=? AND PIN=?";
				PreparedStatement ps=connection.prepareStatement(updateBalanceQuery);
				ps.setDouble(1, balance-amt);
				ps.setString(2, accno);
				ps.setInt(3, pin);
				
				int result=ps.executeUpdate();
				if(result==1)
				{
					System.out.println("Amount debited");
					return true;
				}
			}
		}
		catch (Exception e) {
			System.out.println("Issue in debiting the account");
		}
		return false;
	}
	
	public void transferAmount(String accno,int myPin,double amt,String receiverAccno,String receiverAccHolder)
	{
		try(Connection connection=CreateDBConnection.createConnection();CallableStatement call=connection.prepareCall("(call debitBalance(?,?,?,?))");CallableStatement call2=connection.prepareCall("(call creditBalance(?,?,?,?))"))
		{
			call.setString(2,accno );
			call.setInt(3, myPin);
			call.setDouble(4,amt);
			
			double debit=call.getDouble(1);
			
			call2.execute();
			if(debit>0)
			{
				call2.setString(2, receiverAccno);
				call2.setString(3, receiverAccHolder);
				call2.setDouble(4, debit);
				
				
				double credit=call2.getDouble(1);
			}
			else
			{
				System.out.println("Issue in debit");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}
