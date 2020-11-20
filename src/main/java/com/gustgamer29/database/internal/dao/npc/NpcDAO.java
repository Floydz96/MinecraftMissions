package com.gustgamer29.database.internal.dao.npc;

import com.gustgamer29.database.internal.dao.DAO;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcType;

import java.util.List;

public interface NpcDAO extends DAO<String, INPC> {

    List<INPC> getByType(NpcType type);
}
