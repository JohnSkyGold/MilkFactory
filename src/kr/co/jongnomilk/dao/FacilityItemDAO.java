package kr.co.jongnomilk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.co.jongnomilk.model.FacilityItemData;

public class FacilityItemDAO extends DAO{
	private static FacilityItemDAO facilityItemDAO = new FacilityItemDAO();
	
	public static FacilityItemDAO getFacilityItemDAO() {
		return facilityItemDAO;
	}
	
	public FacilityItemData getFacilityItemData(String serialNumber) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = connect();
			
			StringBuffer query = new StringBuffer();
			query.append("select fa_item_name, fa_category_code, fa_purchasing_date, fa_location, fa_maintenance_date, fa_serialnumber "
					+ "from facility_item "
					+ "where fa_serialnumber = ? ");
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, serialNumber);
			rs = ps.executeQuery();
			if(rs.next()) {
				FacilityItemData facilityItem = new FacilityItemData();
				facilityItem.setItemName(rs.getString(1));
				facilityItem.setCategoryCode(rs.getString(2));
				facilityItem.setPurchasingDate(rs.getDate(3));
				facilityItem.setLocation(rs.getInt(4));
				facilityItem.setMaintenanceDate(rs.getDate(5));
				facilityItem.setSerialNumber(rs.getString(6));
				return facilityItem;
			}
		}catch(Exception e) {
			System.out.println("getFacilityItemData exception: " + e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FacilityItemData item = new FacilityItemData();
		FacilityItemDAO dao = FacilityItemDAO.getFacilityItemDAO();
		item = dao.getFacilityItemData("MS01");
		
		System.out.println(item);
	}

}
