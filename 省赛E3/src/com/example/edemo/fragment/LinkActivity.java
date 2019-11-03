package com.example.edemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.example.edemo.mysql.MyDataBaseHelper;
import com.example.edemo.toast.DiyToast;
import com.example.edemo2.R;

/**
 * 
 * 
 * @author A
 * 
 */
public class LinkActivity extends Fragment {
	private Button btn_add;
	private ListView lv_1;
	private ListView lv_2;
	private ListView lv_3;
	private ListView lv_4;
	private ArrayAdapter<String> mAdapter;
	private List<String> list1 = new ArrayList<String>();
	private List<String> list2 = new ArrayList<String>();
	private List<String> list3 = new ArrayList<String>();
	private EditText et_number, et_name;
	static String control_chuanganqi, control_big_small, control_shebei;
	MyDataBaseHelper dbHelper;
	SQLiteDatabase db;
	int now_ARG = 0;
	boolean link_mode = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_link, container, false);
		initView(view);
		dbHelper = new MyDataBaseHelper(getActivity(), "info.db", null, 2);
		db = dbHelper.getWritableDatabase();
		list1.add("温度");
		list1.add("湿度");
		list1.add("光照");
		list1.add("烟雾");
		list1.add("燃气");
		list1.add("气压");

		list2.add(">");
		list2.add("<");
		list2.add("=");

		list3.add("空调");
		list3.add("DVD");
		list3.add("电视");
		list3.add("射灯全开");
		list3.add("射灯全关");
		list3.add("报警灯开");

		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, list1);
		lv_1.setAdapter(mAdapter);
		lv_1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, list2);
		lv_2.setAdapter(mAdapter);
		lv_2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, list3);
		lv_3.setAdapter(mAdapter);
		lv_3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv_1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				for (int i = 0; i < 99; i++) {
					if (arg2 == i) {
						System.out.println(list1.get(arg2).toString());
						control_chuanganqi = list1.get(arg2);
					}
				}
			}
		});
		lv_2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				for (int i = 0; i < 99; i++) {
					if (arg2 == i) {
						System.out.println(list2.get(arg2).toString());
						control_big_small = list2.get(arg2);
					}
				}
			}
		});
		lv_3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				for (int i = 0; i < 99; i++) {
					if (arg2 == i) {
						System.out.println(list3.get(arg2).toString());
						control_shebei = list3.get(arg2);
					}
				}
			}
		});
		get_data();
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (et_name.getText().toString().isEmpty()
						|| et_number.getText().toString().isEmpty()) {
					DiyToast.showToast(getActivity(), "不能有空白项目");
				} else {
					db.execSQL(
							"insert into link_mode(link_name,link_chuanganqi,link_big_small,link_number,link_shebei)values(?,?,?,?,?)",
							new String[] { et_name.getText().toString(),
									control_chuanganqi, control_big_small,
									et_number.getText().toString(),
									control_shebei });
					get_data();
				}
			}
		});
		lv_4.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Cursor cursor4 = (Cursor) arg0.getItemAtPosition(arg2);
				String string = cursor4.getString(1).toString();
				System.out.println(string);
				if (now_ARG != arg2) {
					now_ARG = arg2;
					link_mode = true;
					Cursor cursor2 = db.rawQuery(
							"select * from link_mode where link_name = ?",
							new String[] { string });
					if (cursor2.moveToNext()) {
						System.out.println(cursor2.getString(cursor2
								.getColumnIndex("link_chuanganqi")));
						link_start(cursor2.getString(cursor2
								.getColumnIndex("link_chuanganqi")), cursor2
								.getString(cursor2
										.getColumnIndex("link_big_small")),
								cursor2.getString(cursor2
										.getColumnIndex("link_number")),
								cursor2.getString(cursor2
										.getColumnIndex("link_shebei")));
					} else {
						DiyToast.showToast(getActivity(), "error");
					}

				} else {
					link_mode = false;
				}
			}
		});
		return view;
	}

	private void get_data() {
		// TODO Auto-generated method stub
		Cursor cursor = db.rawQuery("select * from link_mode", null);
		if (cursor.getCount() != 0) {
			SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
					getActivity(),
					android.R.layout.simple_list_item_multiple_choice, cursor,
					new String[] { "link_name" },
					new int[] { android.R.id.text1 });
			lv_4.setAdapter(cursorAdapter);
			lv_4.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		}
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		btn_add = (Button) view.findViewById(R.id.btn_add);
		lv_1 = (ListView) view.findViewById(R.id.listView1);
		lv_2 = (ListView) view.findViewById(R.id.listView2);
		lv_3 = (ListView) view.findViewById(R.id.listView3);
		lv_4 = (ListView) view.findViewById(R.id.listView4);
		et_name = (EditText) view.findViewById(R.id.et_name);
		et_number = (EditText) view.findViewById(R.id.et_number);
	}

	private void link_start(final String chuanganqi, final String big_small,
			final String number, final String shebnei) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (link_mode) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
					if (chuanganqi.equals("温度")) {
						if (big_small.equals(">")) {
							if (BaseActivity.temp > Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}
						}
						if (big_small.equals("=")) {
							if (BaseActivity.temp == Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							} else {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
							}
						}
						if (big_small.equals("<")) {
							if (BaseActivity.temp < Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
					}
					if (chuanganqi.equals("湿度")) {
						if (big_small.equals(">")) {
							if (BaseActivity.hum > Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
						if (big_small.equals("=")) {
							if (BaseActivity.hum == Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
						if (big_small.equals("<")) {
							if (BaseActivity.hum < Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
					}

					if (chuanganqi.equals("光照")) {
						if (big_small.equals(">")) {
							if (BaseActivity.ill > Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
						if (big_small.equals("=")) {
							if (BaseActivity.ill == Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
						if (big_small.equals("<")) {
							if (BaseActivity.ill < Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
					}

					if (chuanganqi.equals("烟雾")) {
						if (big_small.equals(">")) {
							if (BaseActivity.smo > Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
						if (big_small.equals("=")) {
							if (BaseActivity.smo == Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
						if (big_small.equals("<")) {
							if (BaseActivity.smo < Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
					}

					if (chuanganqi.equals("气压")) {
						if (big_small.equals(">")) {
							if (BaseActivity.press > Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
						if (big_small.equals("=")) {
							if (BaseActivity.press == Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
						if (big_small.equals("<")) {
							if (BaseActivity.press < Integer.valueOf(number)) {
								if (shebnei.equals("空调")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "1",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("DVD")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "5",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("电视")) {
									ControlUtils.control(
											ConstantUtil.INFRARED_1_SERVE, "8",
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全开")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
								if (shebnei.equals("射灯全关")) {
									ControlUtils.control(ConstantUtil.Lamp,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.CLOSE);
								}
								if (shebnei.equals("报警灯开")) {
									ControlUtils.control(
											ConstantUtil.WarningLight,
											ConstantUtil.CHANNEL_ALL,
											ConstantUtil.OPEN);
								}
							}
							if (shebnei.equals("空调")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "1",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("DVD")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "5",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("电视")) {
								ControlUtils.control(
										ConstantUtil.INFRARED_1_SERVE, "8",
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全开")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}
							if (shebnei.equals("射灯全关")) {
								ControlUtils.control(ConstantUtil.Lamp,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.CLOSE);
							}
							if (shebnei.equals("报警灯开")) {
								ControlUtils.control(ConstantUtil.WarningLight,
										ConstantUtil.CHANNEL_ALL,
										ConstantUtil.OPEN);
							}

						}
					}

				}
			}
		}).start();
	}
}
