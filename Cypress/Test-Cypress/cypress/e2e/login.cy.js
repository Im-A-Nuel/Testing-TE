describe("Login Test", () => { 
    beforeEach(function () { 
        cy.visit("https://www.saucedemo.com/"); 
        cy.fixture("user").then((user) => { 
            this.user = user; 
}); 
}); 

it("Login successfully using fixture data", function () { 
    cy.get("input[id='user-name']").type(this.user.validUser); 
    cy.get("input[id='password']").type(this.user.validPassword); 
    cy.get("input[id='login-button']").click(); 
    // Verify successful login 
    cy.url().should("include", "/inventory"); 
    cy.contains("Products").should("be.visible"); 
});

it("Login failed using fixture data", function () { 
    cy.get("input[id='user-name']").type(this.user.invalidUser); 
    cy.get("input[id='password']").type(this.user.invalidPassword); 
    cy.get("input[id='login-button']").click(); 
    cy.get("h3") 
        .should("be.visible") 
        .and("contain","Epic sadface: Username and password do not match any user in this service" 
        ); 
    }); 
});
