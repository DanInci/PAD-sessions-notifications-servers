import { Injectable } from '@angular/core';
import {Socket} from 'ngx-socket-io';

@Injectable({
  providedIn: 'root'
})
export class SocketAPIService {

  constructor(private socket: Socket) { }

  // getRooms() {
  //     return this.socket
  //       .fromEvent('room')
  //       .map( data => data.msg );
  // }
  // getRoom(id: string) {
  //   this.socket.emit('room', id);
  // }

}
