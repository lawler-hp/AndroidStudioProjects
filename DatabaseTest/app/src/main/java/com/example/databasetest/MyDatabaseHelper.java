package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * SQLite 轻量级数据库
*          void	close()：关闭任何打开的数据库对象。
 *         String	getDatabaseName()：返回正在打开的SQLite数据库的名称。
 *         当数据库不可写入时（磁盘空间已满），getReadableDatabase返回的SQ对象只可以读，getWritableDatabase会出现异常
 *         SQLiteDatabase	getReadableDatabase()：创建和/或打开数据库。
 *         SQLiteDatabase	getWritableDatabase()：创建和/或打开将用于读取和写入的数据库。
 *         void	onOpen(SQLiteDatabase db)：打开数据库时调用。
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_BOOK = "CREATE TABLE BOOK (" +
                        "id integer primary key autoincrement," +
                        "author text," +
                        "price integer," +
                        "pages integer," +
                        "name text)";
    private static final String CREATE_CATEGORY = "CREATE TABLE CATEGORY(" +
                        "id integer primary key autoincrement," +
                        "category_name text," +
                        "category_code integer)";
    private Context mContext;

    /**
     * SQLite构造方法
     * @param context   上下文
     * @param name      数据库名称
     * @param factory   SQLite的指针 一般传null
     * @param version   SQLite版本
     */
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    /**
     * 首次创建数据库（对象）时调用。这是创建表和表的初始填充的方法。
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        //跨程序访问数据库内容时，不能使用Toast
//        Toast.makeText(mContext,"创建数据库成功",Toast.LENGTH_LONG).show();
    }

    /**
     * 在需要升级数据库时调用。实现应使用此方法删除表，添加表或执行其他任何升级到新架构版本所需的操作。
     * 如果添加新列，则可以使用ALTER TABLE将它们插入活动表中。如果重命名或删除列，则可以使用ALTER TABLE重命名旧表，然后创建新表，然后使用旧表的内容填充新表。
     * 此方法在事务内执行。如果引发异常，所有更改将自动回滚。
     * @param db    数据库
     * @param oldVersion    旧的数据库版本。
     * @param newVersion    新的数据库版本。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists BOOK");
        db.execSQL("drop table if exists CATEGORY");
        // 重新创建表
        onCreate(db);
    }
}
