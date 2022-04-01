package com.crux.crowd.admin.webui;

import com.crux.crowd.common.util.CrowdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class CrowdUtilsTest{

	Map<Integer,List<String>> data;

	{
		data = new HashMap<>();
		data.put(0, Stream.of("a", "b", "c").collect(Collectors.toList()));
		data.put(1, Stream.of("d", "e", "f").collect(Collectors.toList()));
		data.put(2, Stream.of("g", "h", "i").collect(Collectors.toList()));
	}

	@Test
	@DisplayName("CrowdUtils")
	void testMD5Encryption(){
		String str = "SAIERHAO123";
		String encoded = CrowdUtils.md5Encryption(str);
		log.info("\"{}\" 加密后是 \"{}\"", str, encoded);
	}

	@Test
	@DisplayName("DigestUtils")
	void testMD5Utils(){
		String str = "SAIERHAO123";
		String encoded = DigestUtils.md5DigestAsHex(str.getBytes());
		log.info("\"{}\" 加密后是 \"{}\"", str, encoded);
	}

	@Test
	void testCode(){
		System.out.println("0123456789".substring(0, 5));
	}


	@Test
	@Disabled
	void test01() throws InterruptedException{
		Thread t1 = new Thread(() -> sync(0, Stream.of("A", "B", "C").collect(Collectors.toList())));
		t1.setName("线程1");
		Thread t2 = new Thread(() -> sync(0, Stream.of("B", "C", "D", "F").collect(Collectors.toList())));
		t2.setName("线程2");
		Thread t3 = new Thread(() -> sync(1, Stream.of("T", "Y", "H").collect(Collectors.toList())));
		t3.setName("线程3");
		Thread t4 = new Thread(() -> sync(2, Stream.of("B", "N", "K", "L").collect(Collectors.toList())));
		t4.setName("线程4");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		Thread.sleep(10000);
		System.out.println("修改后的数据是");
		data.entrySet().forEach(System.out::println);
	}

	void sync(Integer index, List<String> newList){
		String thread = Thread.currentThread().getName();
		final List<String> list = data.get(index);

		synchronized(list){
			System.out.println(thread + "进入同步块");
			list.clear();
			System.out.println(thread + "清除完成");
			try{
				Thread.sleep(2000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println(thread + "存放数据" + newList);
			list.addAll(newList);
		}
	}
}
