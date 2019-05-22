import { TestBed } from '@angular/core/testing';

import { SocketAPIService } from './socket-api.service';

describe('SocketAPIService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SocketAPIService = TestBed.get(SocketAPIService);
    expect(service).toBeTruthy();
  });
});
