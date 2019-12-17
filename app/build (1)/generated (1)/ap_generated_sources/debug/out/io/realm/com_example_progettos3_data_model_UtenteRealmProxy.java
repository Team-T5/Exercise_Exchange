package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ImportFlag;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.internal.objectstore.OsObjectBuilder;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_example_progettos3_data_model_UtenteRealmProxy extends com.example.progettos3.data.model.Utente
    implements RealmObjectProxy, com_example_progettos3_data_model_UtenteRealmProxyInterface {

    static final class UtenteColumnInfo extends ColumnInfo {
        long maxColumnIndexValue;
        long idIndex;
        long UserNameIndex;
        long PasswordIndex;
        long FullNameIndex;

        UtenteColumnInfo(OsSchemaInfo schemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Utente");
            this.idIndex = addColumnDetails("id", "id", objectSchemaInfo);
            this.UserNameIndex = addColumnDetails("UserName", "UserName", objectSchemaInfo);
            this.PasswordIndex = addColumnDetails("Password", "Password", objectSchemaInfo);
            this.FullNameIndex = addColumnDetails("FullName", "FullName", objectSchemaInfo);
            this.maxColumnIndexValue = objectSchemaInfo.getMaxColumnIndex();
        }

        UtenteColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new UtenteColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final UtenteColumnInfo src = (UtenteColumnInfo) rawSrc;
            final UtenteColumnInfo dst = (UtenteColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.UserNameIndex = src.UserNameIndex;
            dst.PasswordIndex = src.PasswordIndex;
            dst.FullNameIndex = src.FullNameIndex;
            dst.maxColumnIndexValue = src.maxColumnIndexValue;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private UtenteColumnInfo columnInfo;
    private ProxyState<com.example.progettos3.data.model.Utente> proxyState;

    com_example_progettos3_data_model_UtenteRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UtenteColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.progettos3.data.model.Utente>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(long value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$UserName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.UserNameIndex);
    }

    @Override
    public void realmSet$UserName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.UserNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.UserNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.UserNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.UserNameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$Password() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.PasswordIndex);
    }

    @Override
    public void realmSet$Password(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.PasswordIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.PasswordIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.PasswordIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.PasswordIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$FullName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.FullNameIndex);
    }

    @Override
    public void realmSet$FullName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.FullNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.FullNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.FullNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.FullNameIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Utente", 4, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("UserName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("Password", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("FullName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static UtenteColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new UtenteColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Utente";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Utente";
    }

    @SuppressWarnings("cast")
    public static com.example.progettos3.data.model.Utente createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.example.progettos3.data.model.Utente obj = null;
        if (update) {
            Table table = realm.getTable(com.example.progettos3.data.model.Utente.class);
            UtenteColumnInfo columnInfo = (UtenteColumnInfo) realm.getSchema().getColumnInfo(com.example.progettos3.data.model.Utente.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.example.progettos3.data.model.Utente.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_example_progettos3_data_model_UtenteRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.com_example_progettos3_data_model_UtenteRealmProxy) realm.createObjectInternal(com.example.progettos3.data.model.Utente.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_example_progettos3_data_model_UtenteRealmProxy) realm.createObjectInternal(com.example.progettos3.data.model.Utente.class, json.getLong("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final com_example_progettos3_data_model_UtenteRealmProxyInterface objProxy = (com_example_progettos3_data_model_UtenteRealmProxyInterface) obj;
        if (json.has("UserName")) {
            if (json.isNull("UserName")) {
                objProxy.realmSet$UserName(null);
            } else {
                objProxy.realmSet$UserName((String) json.getString("UserName"));
            }
        }
        if (json.has("Password")) {
            if (json.isNull("Password")) {
                objProxy.realmSet$Password(null);
            } else {
                objProxy.realmSet$Password((String) json.getString("Password"));
            }
        }
        if (json.has("FullName")) {
            if (json.isNull("FullName")) {
                objProxy.realmSet$FullName(null);
            } else {
                objProxy.realmSet$FullName((String) json.getString("FullName"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.progettos3.data.model.Utente createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.example.progettos3.data.model.Utente obj = new com.example.progettos3.data.model.Utente();
        final com_example_progettos3_data_model_UtenteRealmProxyInterface objProxy = (com_example_progettos3_data_model_UtenteRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("UserName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$UserName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$UserName(null);
                }
            } else if (name.equals("Password")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$Password((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$Password(null);
                }
            } else if (name.equals("FullName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$FullName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$FullName(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_example_progettos3_data_model_UtenteRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.progettos3.data.model.Utente.class), false, Collections.<String>emptyList());
        io.realm.com_example_progettos3_data_model_UtenteRealmProxy obj = new io.realm.com_example_progettos3_data_model_UtenteRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.progettos3.data.model.Utente copyOrUpdate(Realm realm, UtenteColumnInfo columnInfo, com.example.progettos3.data.model.Utente object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.progettos3.data.model.Utente) cachedRealmObject;
        }

        com.example.progettos3.data.model.Utente realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.example.progettos3.data.model.Utente.class);
            long pkColumnIndex = columnInfo.idIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_example_progettos3_data_model_UtenteRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.progettos3.data.model.Utente copy(Realm realm, UtenteColumnInfo columnInfo, com.example.progettos3.data.model.Utente newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.progettos3.data.model.Utente) cachedRealmObject;
        }

        com_example_progettos3_data_model_UtenteRealmProxyInterface realmObjectSource = (com_example_progettos3_data_model_UtenteRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.progettos3.data.model.Utente.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.idIndex, realmObjectSource.realmGet$id());
        builder.addString(columnInfo.UserNameIndex, realmObjectSource.realmGet$UserName());
        builder.addString(columnInfo.PasswordIndex, realmObjectSource.realmGet$Password());
        builder.addString(columnInfo.FullNameIndex, realmObjectSource.realmGet$FullName());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_progettos3_data_model_UtenteRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.example.progettos3.data.model.Utente object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.progettos3.data.model.Utente.class);
        long tableNativePtr = table.getNativePtr();
        UtenteColumnInfo columnInfo = (UtenteColumnInfo) realm.getSchema().getColumnInfo(com.example.progettos3.data.model.Utente.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$UserName = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$UserName();
        if (realmGet$UserName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.UserNameIndex, rowIndex, realmGet$UserName, false);
        }
        String realmGet$Password = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$Password();
        if (realmGet$Password != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.PasswordIndex, rowIndex, realmGet$Password, false);
        }
        String realmGet$FullName = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$FullName();
        if (realmGet$FullName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.FullNameIndex, rowIndex, realmGet$FullName, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.progettos3.data.model.Utente.class);
        long tableNativePtr = table.getNativePtr();
        UtenteColumnInfo columnInfo = (UtenteColumnInfo) realm.getSchema().getColumnInfo(com.example.progettos3.data.model.Utente.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.example.progettos3.data.model.Utente object = null;
        while (objects.hasNext()) {
            object = (com.example.progettos3.data.model.Utente) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$UserName = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$UserName();
            if (realmGet$UserName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.UserNameIndex, rowIndex, realmGet$UserName, false);
            }
            String realmGet$Password = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$Password();
            if (realmGet$Password != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.PasswordIndex, rowIndex, realmGet$Password, false);
            }
            String realmGet$FullName = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$FullName();
            if (realmGet$FullName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.FullNameIndex, rowIndex, realmGet$FullName, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.progettos3.data.model.Utente object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.progettos3.data.model.Utente.class);
        long tableNativePtr = table.getNativePtr();
        UtenteColumnInfo columnInfo = (UtenteColumnInfo) realm.getSchema().getColumnInfo(com.example.progettos3.data.model.Utente.class);
        long pkColumnIndex = columnInfo.idIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$UserName = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$UserName();
        if (realmGet$UserName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.UserNameIndex, rowIndex, realmGet$UserName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.UserNameIndex, rowIndex, false);
        }
        String realmGet$Password = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$Password();
        if (realmGet$Password != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.PasswordIndex, rowIndex, realmGet$Password, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.PasswordIndex, rowIndex, false);
        }
        String realmGet$FullName = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$FullName();
        if (realmGet$FullName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.FullNameIndex, rowIndex, realmGet$FullName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.FullNameIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.progettos3.data.model.Utente.class);
        long tableNativePtr = table.getNativePtr();
        UtenteColumnInfo columnInfo = (UtenteColumnInfo) realm.getSchema().getColumnInfo(com.example.progettos3.data.model.Utente.class);
        long pkColumnIndex = columnInfo.idIndex;
        com.example.progettos3.data.model.Utente object = null;
        while (objects.hasNext()) {
            object = (com.example.progettos3.data.model.Utente) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, rowIndex);
            String realmGet$UserName = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$UserName();
            if (realmGet$UserName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.UserNameIndex, rowIndex, realmGet$UserName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.UserNameIndex, rowIndex, false);
            }
            String realmGet$Password = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$Password();
            if (realmGet$Password != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.PasswordIndex, rowIndex, realmGet$Password, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.PasswordIndex, rowIndex, false);
            }
            String realmGet$FullName = ((com_example_progettos3_data_model_UtenteRealmProxyInterface) object).realmGet$FullName();
            if (realmGet$FullName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.FullNameIndex, rowIndex, realmGet$FullName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.FullNameIndex, rowIndex, false);
            }
        }
    }

    public static com.example.progettos3.data.model.Utente createDetachedCopy(com.example.progettos3.data.model.Utente realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.progettos3.data.model.Utente unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.progettos3.data.model.Utente();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.progettos3.data.model.Utente) cachedObject.object;
            }
            unmanagedObject = (com.example.progettos3.data.model.Utente) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_progettos3_data_model_UtenteRealmProxyInterface unmanagedCopy = (com_example_progettos3_data_model_UtenteRealmProxyInterface) unmanagedObject;
        com_example_progettos3_data_model_UtenteRealmProxyInterface realmSource = (com_example_progettos3_data_model_UtenteRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$UserName(realmSource.realmGet$UserName());
        unmanagedCopy.realmSet$Password(realmSource.realmGet$Password());
        unmanagedCopy.realmSet$FullName(realmSource.realmGet$FullName());

        return unmanagedObject;
    }

    static com.example.progettos3.data.model.Utente update(Realm realm, UtenteColumnInfo columnInfo, com.example.progettos3.data.model.Utente realmObject, com.example.progettos3.data.model.Utente newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_example_progettos3_data_model_UtenteRealmProxyInterface realmObjectTarget = (com_example_progettos3_data_model_UtenteRealmProxyInterface) realmObject;
        com_example_progettos3_data_model_UtenteRealmProxyInterface realmObjectSource = (com_example_progettos3_data_model_UtenteRealmProxyInterface) newObject;
        Table table = realm.getTable(com.example.progettos3.data.model.Utente.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);
        builder.addInteger(columnInfo.idIndex, realmObjectSource.realmGet$id());
        builder.addString(columnInfo.UserNameIndex, realmObjectSource.realmGet$UserName());
        builder.addString(columnInfo.PasswordIndex, realmObjectSource.realmGet$Password());
        builder.addString(columnInfo.FullNameIndex, realmObjectSource.realmGet$FullName());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Utente = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{UserName:");
        stringBuilder.append(realmGet$UserName() != null ? realmGet$UserName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{Password:");
        stringBuilder.append(realmGet$Password() != null ? realmGet$Password() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{FullName:");
        stringBuilder.append(realmGet$FullName() != null ? realmGet$FullName() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_example_progettos3_data_model_UtenteRealmProxy aUtente = (com_example_progettos3_data_model_UtenteRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUtente.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUtente.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUtente.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
