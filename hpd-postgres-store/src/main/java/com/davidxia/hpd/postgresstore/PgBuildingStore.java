package com.davidxia.hpd.postgresstore;

import com.davidxia.hpd.model.Building;
import com.davidxia.hpd.store.BuildingReader;
import com.davidxia.hpd.store.BuildingWriter;
import com.google.common.base.Throwables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class PgBuildingStore extends BasePgStore implements BuildingReader, BuildingWriter {

  private PgBuildingStore(final Connection conn, final Executor executor) {
    super(conn, executor);
  }

  public static PgBuildingStore create(final Connection conn, final Executor executor) {
    return new PgBuildingStore(conn, executor);
  }

  @Override
  public CompletionStage<Building> readBuilding(final String buildingId) {
    return null;
  }

  @Override
  public CompletionStage<Void> writeBuilding(final Building building) {
    final String query =
        "INSERT INTO buildings "
        + "(hpd_id, boro_id, house_num, low_house_num, high_house_num, street_name, "
        + "zip, block, lot, bin, community_board, census_tract, management_program, "
        + "dob_building_class_id, legal_stories, legal_class_a, legal_class_b, "
        + "registration_id, life_cycle_id, record_status_id, created, updated) VALUES "
        + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    return supplyAsync(() -> {
      try {
        final PreparedStatement st = conn.prepareStatement(query);
        st.setInt(1, building.hpdId());
        st.setShort(2, building.boro().id());
        st.setString(3, building.houseNum());
        st.setString(4, building.lowHouseNum());
        st.setString(5, building.highHouseNum());
        st.setString(6, building.streetName());
        st.setString(7, building.zip());
        st.setShort(8, building.block());
        st.setShort(9, building.lot());
        st.setInt(10, building.bin());
        st.setInt(11, building.communityBoard());
        st.setString(12, building.censusTract());
        st.setString(13, building.managementProgram());
        st.setInt(14, building.dobBuildingClass().id());
        st.setInt(15, building.legalStories());
        st.setInt(16, building.legalClassA());
        st.setInt(17, building.legalClassB());
        st.setInt(18, building.registrationId());
        st.setShort(19, building.buildingLifeCycle().id());
        st.setShort(20, building.recordStatus().id());
        st.setTimestamp(21, Timestamp.from(building.created()));
        st.setTimestamp(22, Timestamp.from(building.updated()));
        st.executeUpdate();
      } catch (SQLException e) {
        throw Throwables.propagate(e);
      }
      return null;
    });
  }

}
