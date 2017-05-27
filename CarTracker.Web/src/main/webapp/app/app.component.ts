import { Component } from '@angular/core';



@Component({
  selector: 'my-app',
  template: `<navigation></navigation><br/><h1>Hello {{name}}</h1>`,
})
    
export class AppComponent  { 
    name = 'O';
}
