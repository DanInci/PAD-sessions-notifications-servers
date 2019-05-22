import { Player } from './player';

export class Room {
  roomId: string;
  createdAt: string;
  startedAt?: string;
  finishedAt?: string;
  players: Array<Player>;
}
