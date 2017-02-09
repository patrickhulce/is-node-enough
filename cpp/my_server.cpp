//
// Created by Patrick Hulce on 2/4/17.
//

#include <iostream>
#include "crow_all.h"

int main() {
    crow::SimpleApp app;

    CROW_ROUTE(app, "/")([](){
        return "Hello world";
    });

    CROW_ROUTE(app, "/image").methods("POST"_method)([](const crow::request& req){
        std::string x = req.body;
        std::vector<char> bytes(x.begin(), x.end());
        std::cout << x;
        return "boo";
    });

    app.port(18080).multithreaded().run();
};