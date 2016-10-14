package com.davidxia.hpd.postgresstore;

import com.davidxia.hpd.model.Building;
import com.davidxia.hpd.store.BuildingReader;
import com.davidxia.hpd.store.BuildingWriter;
import com.google.common.base.Throwables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PgBuildingStore extends BasePgStore implements BuildingReader, BuildingWriter {

  private static final Logger LOG = LoggerFactory.getLogger(PgBuildingStore.class);

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
      try (final PreparedStatement st = conn.prepareStatement(query)) {
        st.setInt(1, building.hpdId());
        st.setShort(2, building.boro().id());
        st.setString(3, building.houseNum());
        st.setString(4, building.lowHouseNum());
        st.setString(5, building.highHouseNum());
        st.setString(6, building.streetName());
        st.setString(7, building.zip());
        st.setInt(8, building.block());
        st.setShort(9, building.lot());
        setNullableInt(st, 10, building.bin());
        setNullableInt(st, 11, building.communityBoard());
        st.setString(12, building.censusTract());
        st.setString(13, building.managementProgram());

        if (building.dobBuildingClass() == null) {
          st.setNull(14, Types.SMALLINT);
        } else {
          //noinspection ConstantConditions
          st.setShort(14, building.dobBuildingClass().id());
        }

        setNullableInt(st, 15, building.legalStories());
        setNullableInt(st, 16, building.legalClassA());
        setNullableInt(st, 17, building.legalClassB());
        setNullableInt(st, 18, building.registrationId());

        if (building.buildingLifeCycle() == null) {
          st.setNull(19, Types.SMALLINT);
        } else {
          //noinspection ConstantConditions
          st.setShort(19, building.buildingLifeCycle().id());
        }

        if (building.recordStatus() == null) {
          st.setNull(20, Types.SMALLINT);
        } else {
          //noinspection ConstantConditions
          st.setShort(20, building.recordStatus().id());
        }

        st.setTimestamp(21, Timestamp.from(building.created()));
        st.setTimestamp(22, Timestamp.from(building.updated()));

        // TODO (davidxia) This is ugly, but the catch SQLException doesn't seem to catch
        // SQLExceptions thrown by executeUpdate(). Why?
        try {
          if (st.executeUpdate() != 1) {
            LOG.warn("SQL statement {} didn't return 1", st);
          }
        } catch (Throwable t) {
          LOG.error("Could not execute SQL statement {}", st, t);
        }
      } catch (SQLException e) {
        LOG.warn("Could not execute SQL statement", e);
        throw Throwables.propagate(e);
      }
      return null;
    });
  }

  private void setNullableInt(final PreparedStatement st,
                              final int paramIndex,
                              final Integer integer) throws SQLException {
    if (integer == null) {
      st.setNull(paramIndex, Types.INTEGER);
    } else {
      //noinspection ConstantConditions
      st.setInt(paramIndex, integer);
    }
  }
}
