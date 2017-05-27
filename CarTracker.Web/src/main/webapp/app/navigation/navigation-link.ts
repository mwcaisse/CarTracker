export class NavigationLink {
    
    id: string;
    name: string;
    link: string;
    subNavigationLinks: NavigationLink[] = [];
    active: boolean;     

    constructor(id: string, name: string, link: string) {
        this.id = id;
        this.name = name;
        this.link = link;
        
        this.active = false;
    }

    addSubNavigationLink(subLink: NavigationLink) : void {
        this.subNavigationLinks.push(subLink);
    };
    
}