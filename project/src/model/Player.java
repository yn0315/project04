package model;

import java.util.Arrays;
import java.util.Scanner;
import java.util.SplittableRandom;

public class Player {

    Scanner sc = new Scanner(System.in);
    SplittableRandom random = new SplittableRandom();//난수생성기

    /////////////// 필드 ///////////////
    public String id;
    public String[] inventory = new String[]{"기본 검", "보통 활", "기본 갑옷", "고급 지팡이"};
    public int cost;
    public String upgradeCount = "+"; //강화 성공 할 때 마다 뒤에 붙는 숫자 ex) +1, +2, +3...
    int itemCount = 0;
    public int updateCost = 2000;
    int count = 1;//강화할수록 강화비용 올리는 수 2000->4000->6000 이렇게 되도록
    int upgradePercentCount = 9;



    /////////////// 생성자 ///////////////
    public Player() {
        cost = 50000;

    }

    public Player(String[] inventory, int cost, String id) {
        this.inventory = inventory;
        this.cost = cost;
    }

    ////////////////getter,setter///////////////////////
    public String getId() {
        return id;
    }

    public String[] getInventory() {
        return inventory;
    }


    /////////////// 메소드 ///////////////


    //인벤토리를 보여주는 메소드
    public void inventoryView() {

        System.out.println("\n************* Inventory *************\n");
        System.out.printf("아이템 목록: %s", Arrays.toString(inventory));
        System.out.printf("\n돈: %d원 \n", cost);


    }

    // idx 전역변수
    int idxx = 0;


    //강화 메소드
    public void upgradeItem() {


        System.out.println("\n************* Upgrade Item *************");
        System.out.println("\n=============== 강화시스템 설명 ===============");
        System.out.println("1강 -  확률: 90%, 비용: 2000원");
        System.out.println("2강 -  확률: 80%, 비용: 4000원");
        System.out.println("3강 -  확률: 60%, 비용: 6000원");
        System.out.println("4강 -  확률: 40%, 비용: 8000원");
        System.out.println("5강 -  확률: 20%, 비용: 10000원");
        System.out.println("극강 -  확률: 10%, 비용: 20000원");
        System.out.println("\n강화 실패 시 아이템이 즉시 없어지므로 주의하시기 바랍니다!!!!");
        System.out.println("=============================================");

        while (true) {
            inventoryView();
            System.out.println("\n강화 할 아이템를 선택해주시기 바랍니다.");
            System.out.print(">> ");
            String upgradeSelectItem = sc.nextLine();

            // 순차 탐색: 탐색성공시 해당데이터의 인덱스, 실패시 -1
            int idx = -1;

            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i].equals(upgradeSelectItem)) {

                    idx = i;
                    idxx = i;
                    break;
                }
            }// end for

            // 수정 여부 결정
            if (idx != -1) {
                System.out.println(" ");
                System.out.println(upgradeSelectItem + "을(를) 강화할 준비가 완료되었습니다.");
                break;

            } else {
                System.out.println("\n다시 입력해주시기 바랍니다!!");
            }
        }

        while (true) {
            inventoryView();
            System.out.printf("\n강화 비용은 [%d원]입니다.\n", updateCost);
            System.out.println("강화를 시작합니다.");
            System.out.println("\n엔터를 누르세요!!");
            sc.nextLine();


            String answer = "";

            for (int i = 0; i < 10; i++) {//몇 번 반복해야할지 잘 모르겠어서 일단 10으로

                boolean startUpgrade = random.nextInt(1, 101) <= upgradePercentCount--*10;//10%씩 줄어들도록 설정
                ///////다른 아이템을 강화하려고 하면 확률이 줄어든 상태에서 시작하는 문제 발생/////////
                //////기존의 아이템 강화시에는 유지하고 새로운 아이템 강화시 확률 초기화 필요/////////
                //////계속 강화시도시 확률이 마이너스가 돼서 에러뜨는 현상///////////

                //강화시 전체 돈에서 2000 * 1을 먼저 계산한 후에 count++해서 다음 강화비용을 올리는 기능
                cost -= updateCost * count++;

                if (startUpgrade) {

                    System.out.println("\n==================================");
                    System.out.println("=             강화 성공           =");
                    System.out.println("==================================");

                    //강화에 성공하면 해당 아이템에 강화성공 표시를 해주는 기능
                    inventory[idxx] += upgradeCount;


                    System.out.println(" ");
                    System.out.println("     <<       " + inventory[idxx] + "      >>");
                    System.out.println("\n남은 돈: " + cost + "원");

                    System.out.printf("\n강화비용은 [%d원]입니다.", updateCost * count);//숫자 먼저 올리고 계산하도록 앞에 ++붙임
                    System.out.println("\n이어서 강화하시겠습니까? [Y/N]");
                    answer = sc.next();

                    if (answer.equalsIgnoreCase("y")) {//강화 다시시도
                        System.out.println("강화를 시작합니다.");

                    }else {
                        System.out.println("\n메인으로 돌아갑니다.");
                        System.out.println("====================================");
                        return;
                    }


                } else {
                    System.out.println("\n==================================");
                    System.out.println("=             강화 실패           =");
                    System.out.println("==================================");
                    System.out.println("\n [Y]. 아이템 파괴하기  [N]. 두배의 비용을 지불하고 다시 강화하기");
                    System.out.printf("\n※ 두배 강화비용은 [%d원]입니다.\n", updateCost * ((count - 1) * 2));

                    System.out.print(">> ");
                    answer = sc.next();
                    if (answer.equalsIgnoreCase("y")) {//강화 다시시도
                        for (i = idxx; i < inventory.length - 1; i++) {
                            inventory[i] =inventory[i+1];

                        }


                        String[] temp = new String[inventory.length - 1];
                        for (i = 0; i < temp.length; i++) {
                            temp[i] = inventory[i];
                        }

//                        System.out.println(Arrays.toString(inventory));

                        System.out.println("\n==================================");
                        System.out.println("=        퍼            엉         =");
                        System.out.println("==================================");

                        System.out.println("\n아이템이 파괴되었습니다......");

                        System.out.println("\n메인으로 돌아갑니다.");
                        System.out.println("====================================");
                        return;
                    }else if(answer.equalsIgnoreCase("n")){
                        System.out.println("\n강화비용을 두배로 올리고 강화를 다시 시작합니다.");


                        // updateCost * ((count - 1) * 2)은 강화비용을 두배로 처리하는 기능
                        System.out.printf("\n강화비용은 [%d원]입니다.", updateCost * ((count - 1) * 2));





                    }
                }
            }
        }
    }






}