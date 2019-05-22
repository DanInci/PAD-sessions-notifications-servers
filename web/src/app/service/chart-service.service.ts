import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Room} from './room';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ChartServiceService {

  url = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getRooms(): Observable<Array<Room>> {
    return this.http.get<Array<Room>>(this.url + '/room');
  }

  getRoom(id: string): Observable<Room> {
    return this.http.get<Room>(this.url + '/room/' + id);
  }

  getRoomHistory(id: string): Observable<Object> {
    return this.http.get<Object>(this.url + '/room/' + id + '/history');
  }
}
