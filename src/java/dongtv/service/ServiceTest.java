/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service;

/**
 *
 * @author Tran Dong
 */
public class ServiceTest {
    public static void main(String[] args) {
        String password ="123456";
        UserService userService = new UserService();
        System.out.println(userService.checkLogin("admin", "1232456"));
    }
}
