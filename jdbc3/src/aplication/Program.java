package aplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import db.DB;

public class Program {

	public static void main(String[] args) {
	
		Scanner ler = new Scanner(System.in);

		for (int i = 0; i < 2; i++) {
            System.out.println("informe a opção desejada:\n");
            System.out.println("1-imprimir clientes:\n");
            System.out.println("2- inserir cliente:\n");
            
            System.out.println("3- criar tabela:\n");
            String caso = ler.next();
            int casoo = Integer.parseInt(caso);

            switch (casoo){
                case 1:
                	Consulta();
                    break;

                case 2:
                	inserir();
                    break;
                    
                case 3:
                	criarTabela();
                    break;

            }

            System.out.println("\n \n se desejar finalizar digite 1 se quer continuar 2:\n");
            String resp = ler.next();
            int resps = Integer.parseInt(resp);
            if(resps == 1){
                i= 3;
            }else {
                i = 0;
            }
        }

	}
	
	
	public static void Consulta(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM ContaCliente;");
			
			while(rs.next()) {

				System.out.println("Cliente: " + rs.getString("cliente") +", Conta: " + rs.getInt("Conta_id") +", Agencia: " + rs.getInt("agencia_id") +", Saldo: " + rs.getInt("saldo")  );
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			DB.CloseStatement(st);
			DB.CloseResultSet(rs);			
			DB.closeConnection();
		}
		
	}
	
	
	public static void inserir() {
		
		Scanner ler = new Scanner(System.in);
		for (int i = 0; i < 2; i++) {
	        System.out.println("Digite o nome do cliente:");
	        String cliente = ler.next();
	        System.out.println("Informe o numero da conta:");
	        String conta = ler.next();
	        System.out.println("Informe o numero da agencia:");
	        String agencia = ler.next();
	        System.out.println("informe o saldo");
	        double saldo = ler.nextFloat();
	        
	        if(cliente != null && conta != null && agencia != null ) {
	        	ContaCorrente obj = new ContaCorrente();
	           	int ag = Integer.parseInt(agencia);
	        	int cont = Integer.parseInt(conta);
//	        	saldo.replaceAll( "," , "." );
//	        	Double sal = Double.parseDouble( saldo );
	        
	        	obj.setCliente(cliente);
	        	obj.setAgencia(ag);
	        	obj.setConta(cont);
	        	obj.setSaldo(saldo);
	        	
	        	PersisteBanco(obj);
	        	
	        }else {
	        	System.out.println("\n \n Todos os campos devem ser preenchidos. Deseja tentar novamente?");
	        	System.out.println("\n se desejar finalizar digite 1 se quer continuar 2:\n");
	            String resp = ler.next();
	            int resps = Integer.parseInt(resp);
	            if(resps == 1){
	                i= 3;
	            }else {
	                i = 0;
	            }       	
	        }
        }
	}
	
	public static void PersisteBanco(ContaCorrente obj) {
		
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"insert into ContaCliente (cliente,  Conta_id , agencia_id , saldo ) values ('"+ obj.getCliente() +"'," + obj.getConta() + "," + obj.getAgencia() +"," + obj.getSaldo() +");");
						
			int linhasAlteradas = st.executeUpdate();
			if ( linhasAlteradas > 0) {
				System.out.println("registro salvo com sucesso");
			}
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			DB.CloseStatement(st);
			
			DB.ClosePreparedStatement(st);
			DB.closeConnection();
			
		}
		
		
	}
	
	public static void criarTabela() {

		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement( "CREATE TABLE ContaCliente ( cliente VARCHAR(32),  Conta_id int, agencia_id int, saldo float, PRIMARY KEY (Conta_id));" );
			
			int linhasAlteradas = st.executeUpdate();
			System.out.println("Tabela criada!");
					
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			DB.CloseStatement(st);
			DB.ClosePreparedStatement(st);
			DB.closeConnection();
		}
		
		
	}
	

}
