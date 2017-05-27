import { Component } from '@angular/core';

import { NavigationLink } from "./navigation-link"

@Component({
  selector: 'navigation',
  templateUrl: "/cartracker/app/navigation/navigation.template.html"
})
    
export class NavigationComponent  { 
    
    constructor() {
        this.initializeLinks();
    }    
    
    links: NavigationLink[];
    rightLinks: NavigationLink[];

    initializeLinks(): void {
        this.links = [];
        
        this.links.push(new NavigationLink("Home", "Home", "/cartracker/"));
        this.links.push(new NavigationLink("Car", "Car", "/cartracker/car/"));
        this.links.push(new NavigationLink("Trip", "Trip", "/cartracker/trip/"));
        
        var readerLink = new NavigationLink("Log", "Log", "");
        readerLink.addSubNavigationLink(new NavigationLink("Log/Reader", "Reader", "/cartracker/log/reader"));
        
        this.links.push(readerLink);
        
        this.links.push(new NavigationLink("Admin", "Admin", "/cartracker/admin/registrationKeys"));
        
        this.rightLinks = [];
        
        var userLink = new NavigationLink("User", "Mitchell", "");
        
        userLink.addSubNavigationLink(new NavigationLink("User/AuthTokens", "Tokens", ""));
        userLink.addSubNavigationLink(new NavigationLink("User/Logout", "Logout", ""));
        
        this.rightLinks.push(userLink);
        
    }
}
